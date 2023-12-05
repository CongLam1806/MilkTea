/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.order;

import dd.order_details.OrderDetailsDTO;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author MIMI
 */
public class OrderDTO {
    private long orderId;
    private long userId;
    private Timestamp orderDate;
    private double total;
    
    private ArrayList<OrderDetailsDTO> details;

    public OrderDTO(long orderId, long userId, Timestamp orderDate, double total) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.total = total;
    }
    
    public OrderDTO(long orderId, Timestamp orderDate, double total) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.total = total;
    }
    
    public OrderDTO() {
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<OrderDetailsDTO> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<OrderDetailsDTO> details) {
        this.details = details;
    }
    
    
}
