/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.order_details;

import dd.order.OrderDTO;
import dd.product.ProductDTO;
import dd.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author netbeans
 */
public class OrderDetailsDAO {
    private final String ORDER_DETAILS_TABLE_NAME = " " + "TBLORDERDETAILS" + " ";
    private final String PRODUCT_TABLE_NAME = " " + "TBLPRODUCTS" + " ";
    
    public ArrayList<OrderDetailsDTO> getDetailsByOderId(long orderId) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        ArrayList<OrderDetailsDTO> list = new ArrayList<>();

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT p.NAME, p.IMAGE, od.PRICE, od.QUANTITY "
                        + "FROM " + PRODUCT_TABLE_NAME + "p join" + ORDER_DETAILS_TABLE_NAME + "od on p.productId = od.productId " 
                        + "WHERE od.ORDERID = ?";

                stm = con.prepareStatement(sql);

                stm.setLong(1, orderId);

                rs = stm.executeQuery();

                while (rs.next()) {
                    String productName = rs.getString("NAME");
                    String image = rs.getString("IMAGE");
                    double price = rs.getDouble("PRICE");
                    int quantity = rs.getInt("QUANTITY");
                    
                    OrderDetailsDTO detail = new OrderDetailsDTO(quantity, price, new ProductDTO(productName, image));
                    list.add(detail);
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
