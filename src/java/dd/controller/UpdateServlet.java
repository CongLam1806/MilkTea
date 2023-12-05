/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.controller;

import dd.category.CategoryDAO;
import dd.category.CategoryDTO;
import dd.product.ProductValidator;
import dd.product.ProductDAO;
import dd.product.ProductDTO;
import dd.utils.Notification;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author MIMI
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB 
        maxFileSize = 1024 * 1024 * 50, // 50 MB
        maxRequestSize = 1024 * 1024 * 100)   	// 100 MB
public class UpdateServlet extends HttpServlet {

    private final String SEARCH_CONTROLLER = "SearchServlet";
    private final String EDIT_PAGE = "edit.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        String imageUrl = "";
        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                this.loadCategories(request);
                String productIdStr = null;
                String productName = null;
                String priceStr = null;
                String quantityStr = null;
                String description = null;
                String statusStr = null;
                String categoryIdStr = null;
                String oldImageUrl = null;
                List<FileItem> items = upload.parseRequest(request);
                File uploadedFile = null;

                String fileName = null;
                //Get all the parts from request and write it to the file on server
                FileItem fileItem = null;
                String root = getServletContext().getRealPath("/");
                
                // create root image if not existed
                File path = new File(root + "/images");
                if (!path.exists()) {
                    path.mkdirs();
                }
                
                String extension = "";
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        // File process
                        extension = FilenameUtils.getExtension(item.getName());

                        //System.out.println(uploadedFile.getAbsolutePath());
                        fileItem = item;
                    } else {
                        String fieldname = item.getFieldName();
                        String fieldvalue = item.getString();
                        //get All needed parameter
                        switch (fieldname) {
                            case "productId":
                                productIdStr = fieldvalue;
                                break;
                            case "productName":
                                productName = fieldvalue;
                                break;
                            case "price":
                                priceStr = fieldvalue;
                                break;
                            case "quantity":
                                quantityStr = fieldvalue;
                                break;
                            case "description":
                                description = fieldvalue;
                                break;
                            case "status":
                                statusStr = fieldvalue;
                                break;
                            case "categoryId":
                                categoryIdStr = fieldvalue;
                                break;
                            case "image":
                                oldImageUrl = fieldvalue;
                                break;
                        }
                    }
                }

                //default url
                String url = SEARCH_CONTROLLER;

                //validation
                ProductValidator validator = new ProductValidator(productIdStr, productName, description, quantityStr, priceStr, statusStr, categoryIdStr);
                if (validator.hasError()) {
                    url = EDIT_PAGE;
                    request.setAttribute("NOTIFICATION", new Notification("Create failed - Invalid Fields!", false));

                    for (Notification notification : validator.getListChecked()) {
                        if (!notification.isResult()) {
                            request.setAttribute(notification.getErrorField() + "_ERROR", notification.getMessage());
                        }
                    }
                } else {
                    //
                    try {
                        int quantity = validator.getQuantityValid();
                        double price = validator.getPriceValid();
                        boolean status = validator.isStatus();
                        long categoryId = validator.getCategoryIdValid();
                        long productId = validator.getProductIdValid();

                        // resolve path
                        if (extension.isEmpty()) {
                            imageUrl = oldImageUrl;
                        } else {
                            fileName = new Date().getTime() + "." + extension;
                            imageUrl = "/images/" + fileName;
                        }

                        ProductDTO product = new ProductDTO(productId, productName, price, quantity, description, imageUrl, status, categoryId);

                        boolean rs = new ProductDAO().update(product);
                        if (rs) {
                            if (!extension.isEmpty()) {
                                uploadedFile = new File(path + "/" + fileName);
                                fileItem.write(uploadedFile);
                                File removedOldFile = new File(root + oldImageUrl);
                                removedOldFile.delete();
                            }
                            request.setAttribute("NOTIFICATION", new Notification("Update successfully!", true));
                        } else {
                            url = EDIT_PAGE;
                            request.setAttribute("NOTIFICATION", new Notification("Update failed - Database errors!", false));
                        }
                    } catch (SQLException | ClassNotFoundException ex) {
                        log("Error at " + this.getServletName() + ": " + ex.getMessage());
                        request.setAttribute("NOTIFICATION", new Notification("Update failed - Error with database", false));
                    }
                }
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            request.getRequestDispatcher("").forward(request, response);
        }
    }

    private void loadCategories(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        CategoryDAO dao = new CategoryDAO();

        ArrayList<CategoryDTO> list = dao.getAllCategories();
        request.setAttribute("LIST_CATEGORIES", list);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
