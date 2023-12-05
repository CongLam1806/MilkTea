/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.controller;

import dd.role.RoleDAO;
import dd.user.UserDAO;
import dd.user.UserDTO;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String SEARCH_CONTROLLER = "SearchServlet";
    private final String LOGIN_PAGE = "login.jsp";

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

        String userName = request.getParameter("txtUserName");
        String password = request.getParameter("txtPassword");
        
        String url = LOGIN_PAGE; //khi sai username password

        try {

            UserDAO dao = new UserDAO();
            boolean result = dao.checkLogin(userName, password);

            if (result) {

                HttpSession session = request.getSession();
                
                UserDTO loginedUser = dao.getLoginedUser();
                
                String role = "USER";
                
                session.setAttribute("LOGINED_USER", loginedUser);

                RoleDAO roleDao = new RoleDAO();
                url = SEARCH_CONTROLLER;
                if (roleDao.checkAdmin(loginedUser.getRoleId())) { // admin
                    role = "ADMIN";
                }
                
                session.setAttribute("USER_ROLE", role);
            } else {
                request.setAttribute("ERROR_MESSAGE", "Invalid UserName or Password");
            }
        } catch (SQLException ex) {
            log("Error at " + this.getServletName() + ": " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
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
