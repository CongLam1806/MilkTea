/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.utils;

/**
 *
 * @author MIMI
 */
public class Notification {
    private String message;
    private String errorField;
    private boolean result;

    public Notification(String message, boolean result) {
        this.message = message;
        this.result = result;
    }

    public Notification(String message, String errorField, boolean result) {
        this.message = message;
        this.errorField = errorField;
        this.result = result;
    }

    public Notification() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }  

    public String getErrorField() {
        return errorField;
    }

    public void setErrorField(String errorField) {
        this.errorField = errorField;
    }
}
