/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.order;

import dd.order_details.OrderDetailsDTO;
import dd.product.ProductDTO;
import dd.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MIMI
 */
public class OrderDAO {

    private final String ORDER_TABLE_NAME = " " + "TBLORDERS" + " ";
    private final String ORDER_DETAILS_TABLE_NAME = " " + "TBLORDERDETAILS" + " ";
    private final String PRODUCT_TABLE_NAME = " " + "TBLPRODUCTS" + " ";

    public boolean add(ArrayList<OrderDetailsDTO> orderDetailsList, long userId, double total) throws Exception {
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        
        boolean status = false;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                con.setAutoCommit(false);
                //// create a order in DB
                Long orderId = null;
                String sql_1 = "INSERT INTO " + ORDER_TABLE_NAME
                        + "(userId, orderDate, total) "
                        + "VALUES(?, getdate(), ?)";

                //prepare sql stm
                preStm = con.prepareStatement(sql_1, PreparedStatement.RETURN_GENERATED_KEYS);

                //set value for ?s
                preStm.setLong(1, userId);
                preStm.setDouble(2, total); // 

                //exc
                preStm.executeUpdate();

                rs = preStm.getGeneratedKeys(); // getId auto increase
                //check rs
                if (rs.next()) {
                    orderId = rs.getLong(1); // success
                } else {
                    con.rollback();
                    return status;
                }

                // create order details of this order
                String sql_2 = "INSERT INTO " + ORDER_DETAILS_TABLE_NAME
                        + "(productId, quantity, price, orderId) "
                        + "VALUES(?, ?, ?, ?); "
                        + "UPDATE " + PRODUCT_TABLE_NAME
                        + "SET quantity = (SELECT QUANTITY FROM " + PRODUCT_TABLE_NAME + " WHERE productId = ?) - ? "
                        + "WHERE productId = ?";

                preStm = con.prepareStatement(sql_2);

                for (OrderDetailsDTO orderDetails : orderDetailsList) {
                    preStm.setLong(1, orderDetails.getProductId());
                    preStm.setInt(2, orderDetails.getQuantity());
                    preStm.setDouble(3, orderDetails.getPrice());
                    preStm.setLong(4, orderId);
                    
                    preStm.setLong(5, orderDetails.getProductId());
                    preStm.setInt(6, orderDetails.getQuantity());
                    preStm.setLong(7, orderDetails.getProductId());

                    if (preStm.executeUpdate() == 0) {
                        con.rollback();
                        return status;
                    }
                }
                con.commit();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            if (con != null) {
                con.rollback();
            }
            throw new Exception(ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preStm != null) {
                    preStm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                throw  new Exception(ex.getMessage());
            }
        }
        return true;
    }
    
    public ArrayList<OrderDTO> getOrdersByUserId(long userId) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        ArrayList<OrderDTO> list = new ArrayList<>();

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT ORDERID, ORDERDATE, TOTAL "
                        + "FROM " + ORDER_TABLE_NAME
                        + "WHERE USERID = ?";

                stm = con.prepareStatement(sql);

                stm.setLong(1, userId);

                rs = stm.executeQuery();

                while (rs.next()) {
                    long orderId = rs.getLong("orderID");
                    Timestamp orderDate = rs.getTimestamp("orderDate");
                    double total = rs.getDouble("total");
                    
                    OrderDTO order = new OrderDTO(orderId, userId, orderDate, total);
                    list.add(order);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return list;
    }
    
    public ArrayList<OrderDTO> getOrders() throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        ArrayList<OrderDTO> list = new ArrayList<>();

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT ORDERID, ORDERDATE, TOTAL "
                        + "FROM " + ORDER_TABLE_NAME;

                stm = con.prepareStatement(sql);
                
                rs = stm.executeQuery();

                while (rs.next()) {
                    long orderId = rs.getLong("orderID");
                    Timestamp orderDate = rs.getTimestamp("orderDate");
                    double total = rs.getDouble("total");
                    
                    OrderDTO order = new OrderDTO(orderId, orderDate, total);
                    list.add(order);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return list;
    }
}
