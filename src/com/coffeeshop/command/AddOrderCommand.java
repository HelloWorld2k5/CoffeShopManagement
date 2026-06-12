package com.coffeeshop.command;

import com.coffeeshop.model.MenuItem;
import com.coffeeshop.memento.OrderMemento;
import java.util.List;

public class AddOrderCommand implements Command {
    private List<MenuItem> cart;
    private MenuItem item;
    private OrderMemento memento; // Lưu trạng thái trước khi thêm

    public AddOrderCommand(List<MenuItem> cart, MenuItem item) {
        this.cart = cart;
        this.item = item;
    }

    @Override
    public void execute() {
        // 1. Lưu lại trạng thái trước khi thêm (Snapshot)
        this.memento = new OrderMemento(cart); 
        // 2. Thực hiện hành động
        cart.add(item);
    }

    @Override
    public void undo() {
        // Khôi phục lại trạng thái cũ từ memento
        cart.clear();
        cart.addAll(memento.getState());
    }
}