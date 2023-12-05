/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.controller;

import dd.cart.Cart;
import dd.utils.Notification;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author netbeans
 */
@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/UpdateCartServlet"})
public class UpdateCartServlet extends HttpServlet {
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
        
        String[] idsStr = request.getParameterValues("productId");
        String[] quantitiesStr = request.getParameterValues("quantity");
        
        try {
            ArrayList<Long> ids = new ArrayList<>();
            for (String idStr : idsStr) {
                long id = Long.parseLong(idStr);
                ids.add(id);
            }
            
            ArrayList<Integer> quantities = new ArrayList<>();
            for (String quantityStr : quantitiesStr) {
                int quantity = Integer.parseInt(quantityStr);
                quantities.add(quantity);
            }
            
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("CART");
            
            cart.updateCart(ids, quantities);
            session.setAttribute("CART", cart);
        } catch (NumberFormatException ex ) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
            request.setAttribute("NOTIFICATION", new Notification("Update cart failed.", false));
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
