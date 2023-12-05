/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.order_details;

import dd.product.ProductDTO;

/**
 *
 * @author MIMI
 */
public class OrderDetailsDTO {
    private long orderDetailsId;
    private long productId;
    private int quantity;
    private double price;
    private long orderId;
    
    private ProductDTO product;

    public OrderDetailsDTO(long orderDetailsId, long productId, int quantity, double price, long orderId) {
        this.orderDetailsId = orderDetailsId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
    }

    public OrderDetailsDTO(long productId, int quantity, double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
    
    public OrderDetailsDTO(int quantity, double price, ProductDTO product) {
        this.quantity = quantity;
        this.price = price;
        this.product = product;
    }
        
    public OrderDetailsDTO() {
    }

    public long getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(long orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
    
}
