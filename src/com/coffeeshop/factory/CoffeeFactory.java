package com.coffeeshop.factory;

import com.coffeeshop.model.*;

public abstract class CoffeeFactory {
    
    public static MenuItem createMenuItem(int id, String name, String category, 
                                          double price, String description, 
                                          String icon, String status) {
        
        // Sử dụng switch để tạo đúng loại object theo danh mục
        switch (category) {
            case "COFFEE_MACHINE":
            case "COFFEE_TRADITIONAL":
                return new CoffeeItem(id, name, category, price, description, icon, status);
            
            case "BAKERY":
                return new BakeryItem(id, name, category, price, description, icon, status);
                
            case "COMBO":
                return new CoffeeCombo(id, name, category, price, description, icon, status);
                
            default:
                throw new IllegalArgumentException("Loại món không hợp lệ: " + category);
        }
    }
}