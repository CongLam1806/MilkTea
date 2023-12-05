/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.product;

import dd.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author MIMI
 */
public class ProductDAO implements Serializable {

    private final String TABLE_NAME = " " + "TBLPRODUCTS" + " ";

    public ArrayList<ProductDTO> getProductByName(String searchName) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        ArrayList<ProductDTO> products = new ArrayList<>();

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT PRODUCTID, NAME, PRICE, QUANTITY, DESCRIPTION, IMAGE, STATUS, CATEGORYID "
                        + "FROM " + TABLE_NAME
                        + "WHERE NAME LIKE ?";

                stm = con.prepareStatement(sql);

                stm.setString(1, "%" + searchName + "%");

                rs = stm.executeQuery();

                while (rs.next()) {
                    ProductDTO mobile = this.collectDataFromResultSet(rs);

                    products.add(mobile);
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

        return products;
    }

    public ProductDTO getProductById(long productId) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        ProductDTO product = new ProductDTO();

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT PRODUCTID, NAME, PRICE, QUANTITY, DESCRIPTION, IMAGE, STATUS, CATEGORYID "
                        + "FROM " + TABLE_NAME
                        + "WHERE PRODUCTID = ?";

                stm = con.prepareStatement(sql);

                stm.setLong(1, productId);

                rs = stm.executeQuery();
                
                if (rs.next()) {
                    product = this.collectDataFromResultSet(rs);
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

        return product;
    }
    
    
    public boolean delete(String productId) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement preStm = null;
        int rs = 0;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "UPDATE " + TABLE_NAME
                        + "SET STATUS = 0 "
                        + "WHERE PRODUCTID = ? ";

                preStm = con.prepareStatement(sql);

                preStm.setString(1, productId);

                rs = preStm.executeUpdate();
                if (rs != 0) {
                    return true;
                }
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean store(ProductDTO product) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement preStm = null;
        int rs;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "INSERT INTO " + TABLE_NAME
                        + "(NAME, PRICE, QUANTITY, DESCRIPTION, IMAGE, STATUS, CATEGORYID)"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)";

                String name = product.getName();
                double price = product.getPrice();
                int quantity = product.getQuantity();
                String desc = product.getDescription();
                String image = product.getImage();
                boolean status = product.isStatus();
                long categoryId = product.getCategoryId();

                //prepare sql stm
                preStm = con.prepareStatement(sql);

                //set value for ?s
                preStm.setString(1, name);
                preStm.setDouble(2, price);
                preStm.setInt(3, quantity);
                preStm.setString(4, desc);
                preStm.setString(5, image);
                preStm.setBoolean(6, status);
                preStm.setLong(7, categoryId);

                //exc
                rs = preStm.executeUpdate();

                //check rs
                if (rs != 0) {
                    return true;
                }
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean update(ProductDTO product) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement preStm = null;
        int rs = 0;
        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "UPDATE " + TABLE_NAME
                        + "SET NAME = ?, DESCRIPTION = ?, PRICE = ?, QUANTITY = ?, IMAGE = ?, STATUS = ?, CATEGORYID = ? "
                        + "WHERE PRODUCTID = ?";

                //prepare sql stm
                preStm = con.prepareStatement(sql);

                //set value for ?s
                preStm.setString(1, product.getName());
                preStm.setString(2, product.getDescription());
                preStm.setDouble(3, product.getPrice());
                preStm.setInt(4, product.getQuantity());
                preStm.setString(5, product.getImage());
                preStm.setBoolean(6, product.isStatus());
                preStm.setLong(7, product.getCategoryId());
                preStm.setLong(8, product.getProductId());

                //exc
                rs = preStm.executeUpdate();

                //check rs
                if (rs != 0) {
                    return true;
                }
            }
        } finally {
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public ArrayList<ProductDTO> getProductByPriceRange(double minPrice, double maxPrice) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        ArrayList<ProductDTO> products = new ArrayList<>();

        try {
            con = DBUtils.getConnection();
            if (con != null) {
                String sql = "SELECT PRODUCTID, NAME, PRICE, QUANTITY, DESCRIPTION, IMAGE, STATUS, CATEGORYID "
                        + "FROM " + TABLE_NAME
                        + "WHERE PRICE BETWEEN ? AND ? AND STATUS = 1 ";

                stm = con.prepareStatement(sql);

                stm.setDouble(1, minPrice);
                stm.setDouble(2, maxPrice);

                rs = stm.executeQuery();

                while (rs.next()) {
                    ProductDTO mobile = this.collectDataFromResultSet(rs);
                    products.add(mobile);
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

        return products;
    }

    private ProductDTO collectDataFromResultSet(ResultSet rs) throws SQLException {
        long productId = rs.getLong(1);
        String name = rs.getString(2);
        double price = rs.getDouble(3);
        int quantity = rs.getInt(4);
        String description = rs.getString(5);
        String image = rs.getString(6);
        boolean status = rs.getBoolean(7);
        long categoryId = rs.getLong(8);

        ProductDTO product = new ProductDTO(productId, name, price, quantity, description, image, status, categoryId);
        return product;
    }
}
