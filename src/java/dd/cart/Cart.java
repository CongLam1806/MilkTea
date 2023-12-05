/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dd.cart;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author MIMI
 */
public class Cart {
    private HashMap<Long, Integer> items;

    public HashMap<Long, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<Long, Integer> items) {
        this.items = items;
    }
    
    public void addToCart(long key) {
        if(items == null) {
            items = new HashMap<>();
        }
        int quantity = 1;
        if(items.containsKey(key)) {
            quantity = items.get(key) + 1;
        }
        
        items.put(key, quantity);
    }
    public boolean removeFromCart(long key) {
        if(items == null) {
            return false;
        }
        if(items.containsKey(key)) {
            items.remove(key);
            if(items.isEmpty()) {
                items = null;
            }
            return true;
        }
        return false;
    }
    
    public void updateCart(ArrayList<Long> keys, ArrayList<Integer> quantities) {
        items = new HashMap<>();
        
        for (int i = 0; i < keys.size(); i++) {
            items.put(keys.get(i), quantities.get(i));
        }
    }
}
