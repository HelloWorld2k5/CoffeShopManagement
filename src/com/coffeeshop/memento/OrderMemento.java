package com.coffeeshop.memento;

import com.coffeeshop.model.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class OrderMemento {
    private final List<MenuItem> state;

    public OrderMemento(List<MenuItem> cartItems) {
        // Lưu một bản sao (deep copy) để đảm bảo an toàn dữ liệu
        this.state = new ArrayList<>(cartItems);
    }

    public List<MenuItem> getState() {
        return state;
    }
}