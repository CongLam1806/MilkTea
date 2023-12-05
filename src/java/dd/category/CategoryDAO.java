/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.category;

import dd.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author netbeans
 */
public class CategoryDAO {
    private final String TABLE_NAME = " " + "TBLCATEGORIES" + " ";
    
    public CategoryDTO getCategoryById(long categoryId)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB
            con = DBUtils.getConnection();
            if (con != null) {
                //2. Create SQL Statement String
                String sql = "SELECT NAME "
                        + "FROM " + TABLE_NAME
                        + "WHERE CATEGORYID = ?";
                //3. Create Statement to set SQL
                preStm = con.prepareStatement(sql);

                preStm.setLong(1, categoryId);

                //4. Execute Query
                rs = preStm.executeQuery();
                
                //5. Process
                if (rs.next()) {
                    String name = rs.getString("name");
                    CategoryDTO category = new CategoryDTO(categoryId, name);
                    return category;
                }
            } //end if connection is existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }
    
    public ArrayList<CategoryDTO> getAllCategories()
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        ArrayList<CategoryDTO> list = new ArrayList<>();
        try {
            //1. Connect DB
            con = DBUtils.getConnection();
            if (con != null) {
                //2. Create SQL Statement String
                String sql = "SELECT CATEGORYID, NAME "
                        + "FROM " + TABLE_NAME;
                //3. Create Statement to set SQL
                preStm = con.prepareStatement(sql);

                //4. Execute Query
                rs = preStm.executeQuery();
                
                //5. Process
                while (rs.next()) {
                    long categoryId = rs.getLong("categoryId");
                    String name = rs.getString("name");
                    CategoryDTO category = new CategoryDTO(categoryId, name);
                    list.add(category);
                }
            } //end if connection is existed
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preStm != null) {
                preStm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return list;
    }
}
