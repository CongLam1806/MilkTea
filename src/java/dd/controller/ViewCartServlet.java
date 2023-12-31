/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.controller;

import dd.cart.Cart;
import dd.product.ProductDAO;
import dd.product.ProductDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "ViewCartServlet", urlPatterns = {"/ViewCartServlet"})
public class ViewCartServlet extends HttpServlet {

    private final String VIEW_CART_PAGE = "view_cart.jsp";

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

        ProductDAO dao = new ProductDAO();

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("CART");

        if (cart == null) {
            cart = new Cart();
        }

        ArrayList<ProductDTO> products = new ArrayList<>();
        double total = 0;
        try {
            HashMap<Long, Integer> items = cart.getItems();
            if (items == null) {
                items = new HashMap<>();
            }
            for (long productId : items.keySet()) {
                ProductDTO product = dao.getProductById(productId);
                if (product != null) {
                    products.add(product);
                    total += product.getPrice() * items.get(productId);
                } else {
                    throw new Exception("Not found product: " + product.getName() + " - Remove it and try again, please!");
                }
            }
        } catch (Exception ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        }
        request.setAttribute("TOTAL", total);
        request.setAttribute("CART_ITEMS", products);
        request.getRequestDispatcher(VIEW_CART_PAGE).forward(request, response);
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
