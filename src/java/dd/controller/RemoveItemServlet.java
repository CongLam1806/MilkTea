/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.controller;

import dd.cart.Cart;
import dd.product.ProductDAO;
import dd.utils.Notification;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author MIMI
 */
@WebServlet(name = "RemoveItemServlet", urlPatterns = {"/RemoveItemServlet"})
public class RemoveItemServlet extends HttpServlet {
    private final String VIEW_CART_CONTROLLER = "ViewCartServlet";
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

        if (productIdStr == null) {
            request.setAttribute("NOTIFICATION", new Notification("Remove item failed - invalid product id", false));
        } else {
            long productId = Long.parseLong(productIdStr);

            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("CART");

            if (cart == null) {
                cart = new Cart();
            }

            if (cart.removeFromCart(productId)) {
                request.setAttribute("NOTIFICATION", new Notification("Remove item successfully", true));
            } else {
                request.setAttribute("NOTIFICATION", new Notification("Remove item failed - invalid product id", false));
            }
        }
        
        request.getRequestDispatcher(VIEW_CART_CONTROLLER).forward(request, response);
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
