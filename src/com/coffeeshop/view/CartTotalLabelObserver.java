package com.coffeeshop.view;

import com.coffeeshop.config.AppConfig;
import com.coffeeshop.observer.CartSubject;
import com.coffeeshop.observer.Observer;
import com.coffeeshop.observer.Subject;
import com.coffeeshop.util.MoneyUtil;

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
                double subtotal = cart.getCartItems().stream()
                        .mapToDouble(order -> order.getItem().getFinalPrice() * order.getQuantity())
                        .sum();
                double vatRate = AppConfig.getInstance().getVatRate();
                double total = subtotal * (1 + vatRate);
                totalLabel.setText("Tổng tiền: " + MoneyUtil.format(total));
            }
        }
    }
}