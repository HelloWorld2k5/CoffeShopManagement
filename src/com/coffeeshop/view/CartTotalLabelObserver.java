package com.coffeeshop.view;

import com.coffeeshop.observer.CartSubject;
import com.coffeeshop.observer.Observer;
import com.coffeeshop.observer.Subject;
import javax.swing.JLabel;

public class CartTotalLabelObserver implements Observer {
    private final JLabel totalLabel; 

    public CartTotalLabelObserver(JLabel totalLabel) {
        this.totalLabel = totalLabel;
    }

    @Override
    public void update(Subject subject) {
        if (subject instanceof CartSubject) {
            CartSubject cart = (CartSubject) subject;
            
            if (cart.getCartItems() != null) {
                // SỬA LỖI Ở ĐÂY: Sử dụng .getItem() và .getQuantity() thay vì truy cập trực tiếp
                double total = cart.getCartItems().stream()
                                   .mapToDouble(order -> order.getItem().getFinalPrice() * order.getQuantity())
                                   .sum();
                                   
                totalLabel.setText("Tổng tiền: " + String.format("%,.0f", total) + "đ");
            }
        }
    }
}