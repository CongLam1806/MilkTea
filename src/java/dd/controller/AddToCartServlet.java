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
import java.sql.SQLException;
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
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private final String SEARCH_SERVLET = "SearchServlet";

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

        ProductDAO dao = new ProductDAO();

        try {
            long productId = Long.parseLong(productIdStr);
            
            if (dao.getProductById(productId) == null) {
                request.setAttribute("NOTIFICATION", new Notification("Add to cart fail - Invalid product id", false));
            } else {
                HttpSession session = request.getSession();
                Cart cart = (Cart) session.getAttribute("CART"); 
                
                if (cart == null) {
                    cart = new Cart();
                } 
                
                cart.addToCart(productId);
                session.setAttribute("CART", cart);
                request.setAttribute("NOTIFICATION", new Notification("Add to cart successfully!", true));
            }
        } catch (SQLException | ClassNotFoundException | NumberFormatException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        }

        request.getRequestDispatcher(SEARCH_SERVLET).forward(request, response);
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
