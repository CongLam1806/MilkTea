/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.role;

/**
 *
 * @author netbeans
 */
public class RoleDTO {
    private long roleID;
    private String name;

    public RoleDTO(long roleID, String name) {
        this.roleID = roleID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRoleID() {
        return roleID;
    }

    public void setRoleID(long roleID) {
        this.roleID = roleID;
    }
    
    
}
