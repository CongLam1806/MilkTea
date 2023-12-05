/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.role;

import dd.user.UserDTO;
import dd.utils.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author netbeans
 */
public class RoleDAO {
    
    private final String TABLE_NAME = " " + "TBLROLES" + " ";
    
    private final String USER_ROLE = "USER";
    
    private final String ADMIN_ROLE = "ADMIN";
    
    private boolean checkRoleByName(long roleId, String roleName)
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
                        + "WHERE ROLEID = ? AND NAME = ?";
                //3. Create Statement to set SQL
                preStm = con.prepareStatement(sql);

                preStm.setLong(1, roleId);
                preStm.setString(2, roleName);

                //4. Execute Query
                rs = preStm.executeQuery();
                
                //5. Process
                if (rs.next()) {
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
    
    public boolean checkAdmin(long roleId) throws SQLException, ClassNotFoundException {
        return this.checkRoleByName(roleId, ADMIN_ROLE);
    }
    
    public boolean checkUser(long roleId) throws SQLException, ClassNotFoundException {
        return this.checkRoleByName(roleId, USER_ROLE);
    }
}
