/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.product;

import java.io.Serializable;

/**
 *
 * @author MIMI
 */
public class ProductDTO implements Serializable {

    private long productId;
    private String name;
    private double price;
    private int quantity;
    private String description;
    private String image;
    private boolean status;
    private long categoryId;

    public ProductDTO(long productId, String name, double price, int quantity, String description, String image, boolean status, long categoryId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.image = image;
        this.status = status;
        this.categoryId = categoryId;
    }

    public ProductDTO(String name, double price, int quantity, String description, String image, boolean status, long categoryId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.image = image;
        this.status = status;
        this.categoryId = categoryId;
    }
    
    public ProductDTO(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public ProductDTO() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
