/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.controller;

import dd.cart.Cart;
import dd.product.ProductDAO;
import dd.product.ProductDTO;
import dd.order.OrderDAO;
import dd.order_details.OrderDetailsDTO;
import dd.user.UserDTO;
import dd.utils.Notification;
import java.io.IOException;
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
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    private final String SEARCH_CONTROLLER = "SearchServlet"; //then goto home page
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

        String url = VIEW_CART_CONTROLLER;
        
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
                System.out.println(productId);
                ProductDTO product = dao.getProductById(productId);
                
                if (product != null) {
                    product.setQuantity(cart.getItems().get(productId));
                    products.add(product);
                    total += product.getQuantity() * product.getPrice();
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        }
        UserDTO currentUser = ((UserDTO) session.getAttribute("LOGINED_USER"));
        if (currentUser == null) {
            // must login to checkout
            request.setAttribute("NOTIFICATION", new Notification("Checkout failed! You must login to checkout!", false));
            request.getRequestDispatcher(url).forward(request, response);
            return;
        }
        long currentUserId = currentUser.getUserId();

        //parse value of mobiles to order details list
        ArrayList<OrderDetailsDTO> orderDetailsList = new ArrayList<>();
        for (ProductDTO product : products) {
            OrderDetailsDTO orderDetails
                    = new OrderDetailsDTO(product.getProductId(), product.getQuantity(), product.getPrice());
            orderDetailsList.add(orderDetails);
        }

        OrderDAO orderDao = new OrderDAO();

        

        try {
            if (!orderDetailsList.isEmpty() && orderDao.add(orderDetailsList, currentUserId, total)) {
                session.setAttribute("CART", null);
                url = SEARCH_CONTROLLER;
                request.setAttribute("NOTIFICATION", new Notification("Checkout Successfully!", true));
            } else {
                request.setAttribute("NOTIFICATION", new Notification("Checkout Failed - DB error, please try again!", false));
            }
        } catch (Exception ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
            request.setAttribute("NOTIFICATION", new Notification("Checkout Failed - DB error, please try again!", false));
        }

        request.getRequestDispatcher(url).forward(request, response);
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
