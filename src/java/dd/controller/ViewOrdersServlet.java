/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.controller;

import dd.order.OrderDAO;
import dd.order.OrderDTO;
import dd.order_details.OrderDetailsDAO;
import dd.order_details.OrderDetailsDTO;
import dd.user.UserDTO;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author netbeans
 */
@WebServlet(name = "ViewOrdersServlet", urlPatterns = {"/ViewOrdersServlet"})
public class ViewOrdersServlet extends HttpServlet {
    private final String SEARCH_CONTROLLER = "SearchServlet";
    private final String VIEW_ORDERS_PAGE = "view_orders.jsp";
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
        
        String url = SEARCH_CONTROLLER;
        try {
            String isAdmin = request.getParameter("isAdmin");
            
            HttpSession session = request.getSession();
            
            UserDTO currentUser = (UserDTO) session.getAttribute("LOGINED_USER");
            
            long userId = currentUser.getUserId();
            
            OrderDAO dao = new OrderDAO();
            ArrayList<OrderDTO> orders = null;
            if (isAdmin.equals("true")) {
                orders = dao.getOrders();
            } else {
                orders = dao.getOrdersByUserId(userId);
            }
            
            OrderDetailsDAO detailsDao = new OrderDetailsDAO();
            
            for (OrderDTO order : orders) {
                ArrayList<OrderDetailsDTO> details = detailsDao.getDetailsByOderId(order.getOrderId());
                
                order.setDetails(details);
            }
            
            request.setAttribute("ORDERS", orders);
            url = VIEW_ORDERS_PAGE;
        } catch (Exception ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
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
