/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.product;

import dd.category.CategoryDAO;
import dd.user.UserDAO;
import dd.utils.Notification;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MIMI
 */
public class ProductValidator {

    private String productId;
    private String productName;
    private String description;
    private String quantity;
    private String price;
    private String status;
    private String categoryId;
    private ArrayList<Notification> listChecked;
    private String dbError;

    public ProductValidator(String productName, String description, String quantity, String price, String status, String categoryId) {
        listChecked = new ArrayList<>();
        listChecked.add(setProductName(productName));
        listChecked.add(setDescription(description));
        listChecked.add(setQuantity(quantity));
        listChecked.add(setPrice(price));
        setStatus(status);
        listChecked.add(setCategoryId(categoryId));
    }
    
    public ProductValidator(String productId, String productName, String description, String quantity, String price, String status, String categoryId) {
        listChecked = new ArrayList<>();
        listChecked.add(setProductId(productId));
        listChecked.add(setProductName(productName));
        listChecked.add(setDescription(description));
        listChecked.add(setQuantity(quantity));
        listChecked.add(setPrice(price));
        setStatus(status);
        listChecked.add(setCategoryId(categoryId));
    }

    public ProductValidator() {
    }

    public boolean isStatus() {
        return Boolean.parseBoolean(this.status);
    }

    private boolean setStatus(String status) {
        this.status = status;
        return status.equalsIgnoreCase("true");
    }

    public String getProductName() {
        return productName;
    }

    private Notification setProductName(String productName) {
        final String ERROR_FIELD_NAME = "productName";
        this.productName = productName;
        if (productName == null || productName.isEmpty()) {
            return new Notification("Product NAME must not be empty!", ERROR_FIELD_NAME, false);
        }
        if (productName.length() > 50) {
            return new Notification("Product NAME must be less than or equal 20 characters!", ERROR_FIELD_NAME, false);
        }
        return new Notification("", true);
    }

    public String getDescription() {
        return description;
    }

    private Notification setDescription(String description) {
        final String ERROR_FIELD_NAME = "description";
        this.description = description;
        if (description == null || description.isEmpty()) {
            return new Notification("DESCRIPTION must not be empty!", ERROR_FIELD_NAME, false);
        }
        if (description.length() > 300) {
            return new Notification("DESCRIPTION's length must be less than 300 (chars)!", ERROR_FIELD_NAME, false);
        }
        return new Notification("", true);
    }

    public int getQuantityValid() {
        try {
            return Integer.parseInt(this.quantity);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
    
    public long getCategoryIdValid() {
        try {
            return Long.parseLong(this.categoryId);
        } catch (NumberFormatException ex) {
            return 1;
        }
    }
    
    public long getProductIdValid() {
        try {
            return Long.parseLong(this.productId);
        } catch (NumberFormatException ex) {
            return 1;
        }
    }

    private Notification setQuantity(String quantity) {
        final String ERROR_FIELD_NAME = "quantity";
        this.quantity = quantity;

        if (quantity == null || quantity.isEmpty()) {
            return new Notification("QUANTITY must not be empty!", ERROR_FIELD_NAME, false);
        }
        int quantityInt;
        try {
            quantityInt = Integer.parseInt(quantity);
        } catch (NumberFormatException ex) {
            return new Notification("QUANTITY must be an integer number!", ERROR_FIELD_NAME, false);
        }

        if (quantityInt < 0) {
            return new Notification("QUANTITY must be a positive number!", ERROR_FIELD_NAME, false);
        }

        return new Notification("", true);
    }

    public double getPriceValid() {
        try {
            return Double.parseDouble(this.price);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private Notification setPrice(String price) {
        final String ERROR_FIELD_NAME = "price";
        this.price = price;

        if (price == null || price.isEmpty()) {
            return new Notification("PRICE must not be empty!", ERROR_FIELD_NAME, false);
        }

        double priceDouble;

        try {
            priceDouble = Double.parseDouble(price);
        } catch (NumberFormatException ex) {
            return new Notification("PRICE must be a number!", ERROR_FIELD_NAME, false);
        }
        if(priceDouble > 1000000){
            return new Notification("PRICE must be less than 1000000VND!", ERROR_FIELD_NAME, false);
        }

        if (priceDouble < 0) {
            return new Notification("PRICE must be a positive number!", ERROR_FIELD_NAME, false);
        }

        return new Notification("", true);
    }
    
    private Notification setCategoryId(String categoryId) {
        final String ERROR_FIELD_NAME = "categoryId";
        this.categoryId = categoryId;
        long id = 0;
        try {
            id = Long.parseLong(categoryId);
        }
        catch (NumberFormatException ex) {
        }
        CategoryDAO categoryDao = new CategoryDAO();
        try {
            if (categoryDao.getCategoryById(id) == null) {
                return new Notification("Not found category id", ERROR_FIELD_NAME, false);
            }
        } catch (Exception ex) {
            return new Notification("DB Error", ERROR_FIELD_NAME, false);
        }
        
        return new Notification("", true);
    }
    
    private Notification setProductId(String productId) {
        final String ERROR_FIELD_NAME = "productId";
        this.productId = productId;
        long id = 0;
        try {
            id = Long.parseLong(productId);
        }
        catch (NumberFormatException ex) {
        }
        ProductDAO productDao = new ProductDAO();
        try {
            if (productDao.getProductById(id) == null) {
                return new Notification("Not found product id", ERROR_FIELD_NAME, false);
            }
        } catch (Exception ex) {
            return new Notification("DB Error", ERROR_FIELD_NAME, false);
        }
        
        return new Notification("", true);
    }

    public ArrayList<Notification> getListChecked() {
        return listChecked;
    }

    public boolean hasError() {
        for (Notification notification : listChecked) {
            if (!notification.isResult()) {
                return true;
            }
        }
        return false;
    }

    public String getDbError() {
        return dbError;
    }

    public void setDbError(String dbError) {
        this.dbError = dbError;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }
}
