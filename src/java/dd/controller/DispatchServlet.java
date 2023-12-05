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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author MIMI
 */
@WebServlet(name = "DispatchServlet", urlPatterns = {"/DispatchServlet"})
public class DispatchServlet extends HttpServlet {

    private final String LOGIN_PAGE = "login.jsp";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String UPDATE_CONTROLLER = "UpdateServlet";
    private final String DELETE_CONTROLLER = "DeleteServlet";
    private final String SEARCH_CONTROLLER = "SearchServlet";
    private final String CREATE_PAGE = "create.jsp";
    private final String EDIT_CONTROLLER = "EditServlet";
    private final String STORE_CONTROLLER = "StoreServlet";
    private final String ADD_TO_CART_CONTROLLER = "AddToCartServlet";
    private final String VIEW_CART_CONTROLLER = "ViewCartServlet";
    private final String REMOVE_ITEM_CONTROLLER = "RemoveItemServlet";
    private final String UPDATE_CART_CONTROLLER = "UpdateCartServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String CHECKOUT_CONTROLLER = "CheckoutServlet";
    private final String VIEW_ORDERS_CONTROLLER = "ViewOrdersServlet";

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
        PrintWriter out = response.getWriter();

        String button = request.getParameter("btAction");

        String url = SEARCH_CONTROLLER;
        try {
            if (button == null) {
                //do nothing
            } else if (button.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (button.equals("Update")) {
                url = UPDATE_CONTROLLER;
            } else if (button.equals("Delete")) {
                url = DELETE_CONTROLLER;
            } else if (button.equals("Search")) {
                url = SEARCH_CONTROLLER;
            } else if (button.equals("Create")) {
                this.loadCategories(request);
                url = CREATE_PAGE;
            } else if (button.equals("Edit")) {
                url = EDIT_CONTROLLER;
            } else if (button.equals("Store")) {
                url = STORE_CONTROLLER;
            } else if (button.equals("Add to cart")) {
                url = ADD_TO_CART_CONTROLLER;
            } else if (button.equals("View cart")) {
                url = VIEW_CART_CONTROLLER;
            } else if (button.equals("Update cart")) {
                url = UPDATE_CART_CONTROLLER;
            } else if (button.equals("Remove from cart")) {
                url = REMOVE_ITEM_CONTROLLER;
            } else if (button.equals("Logout")) {
                url = LOGOUT_CONTROLLER;
            } else if (button.equals("Checkout")) {
                url = CHECKOUT_CONTROLLER;
            } else if (button.equals("View Orders")) {
                url = VIEW_ORDERS_CONTROLLER;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        }

        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
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
