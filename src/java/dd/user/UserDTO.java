/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.user;

import java.io.Serializable;

/**
 *
 * @author MIMI
 */
public class UserDTO implements Serializable {
    private long userId;
    private String userName;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String email;
    private long roleId;

    public UserDTO(String password, String name, String phone, String address, String email, long roleId, String userName) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.roleId = roleId;
        this.userName = userName;
    }

    public UserDTO(String userName, String name, long roleId) {
        this.userName = userName;
        this.name = name;
        this.roleId = roleId;
    }
    
    public UserDTO(long userId, String userName, String name, long roleId) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.roleId = roleId;
    }
    
    public UserDTO() {
    }
    
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
