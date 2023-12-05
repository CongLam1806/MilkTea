/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.user;

import dd.utils.DBUtils;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author MIMI
 */
public class UserDAO implements Serializable {

    private final String TABLE_NAME = " " + "TBLUSERS" + " ";

    private UserDTO loginedUser;

    public UserDTO getLoginedUser() {
        return loginedUser;
    }

    public boolean checkLogin(String userName, String password)
            throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement preStm = null;
        ResultSet rs = null;
        try {
            //1. Connect DB
            con = DBUtils.getConnection();
            if (con != null) {
                //2. Create SQL Statement String
                String sql = "SELECT USERID, NAME, ROLEID "
                        + "FROM " + TABLE_NAME
                        + "WHERE USERNAME = ? AND PASSWORD = ?";
                //3. Create Statement to set SQL
                preStm = con.prepareStatement(sql);

                preStm.setString(1, userName);
                preStm.setString(2, password);

                //4. Execute Query
                rs = preStm.executeQuery();
                
                //5. Process
                if (rs.next()) {
                    long userId = rs.getLong("userID");
                    String name = rs.getString("name");
                    int roleId = rs.getInt("roleID");
                    
                    loginedUser = new UserDTO(userId, userName, name, roleId);

                    return true;
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
        return false;
    }
}
