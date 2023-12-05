/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.controller;

import dd.category.CategoryDAO;
import dd.category.CategoryDTO;
import dd.product.ProductDAO;
import dd.product.ProductDTO;
import dd.utils.Notification;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author netbeans
 */
@WebServlet(name = "EditServlet", urlPatterns = {"/EditServlet"})
public class EditServlet extends HttpServlet {
    private final String EDIT_PAGE = "edit.jsp";
    private final String ADMIN_HOME_PAGE = "admin_home.jsp";
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
        
        String productIdStr = request.getParameter("productId");
        String url = EDIT_PAGE;
        try {
            int productId = Integer.parseInt(productIdStr);
            ProductDAO dao = new ProductDAO();
            
            ProductDTO product = dao.getProductById(productId);
            
            if (product == null) {
                url = ADMIN_HOME_PAGE;
                request.setAttribute("NOTIFICATION", new Notification("Not found product to edit.", false));
            } else {
                this.loadCategories(request);
                request.setAttribute("UPDATE_PRODUCT", product);
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
            request.setAttribute("NOTIFICATION", new Notification("Edit failed - Error with database", false));
        }
        
        request.getRequestDispatcher(url).forward(request, response);
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
