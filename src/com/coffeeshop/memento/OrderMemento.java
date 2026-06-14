package com.coffeeshop.memento;

import com.coffeeshop.observer.CartSubject.OrderItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMemento {
    private final Map<Integer, List<OrderItem>> state;

    public OrderMemento(Map<Integer, List<OrderItem>> tableCarts) {
        this.state = new HashMap<>();
        // Thực hiện Deep Copy để đảm bảo state cũ không bị thay đổi bởi tham chiếu
        for (Map.Entry<Integer, List<OrderItem>> entry : tableCarts.entrySet()) {
            List<OrderItem> newList = new ArrayList<>();
            for (OrderItem item : entry.getValue()) {
                newList.add(item.deepCopy()); // Ứng dụng prototype
            }
            this.state.put(entry.getKey(), newList);
        }
    }

    public Map<Integer, List<OrderItem>> getState() {
        // Trả ra một Deep Copy khác để tránh bị bên ngoài can thiệp ngược vào Memento
        Map<Integer, List<OrderItem>> copy = new HashMap<>();
        for (Map.Entry<Integer, List<OrderItem>> entry : state.entrySet()) {
            List<OrderItem> newList = new ArrayList<>();
            for (OrderItem item : entry.getValue()) {
                newList.add(item.deepCopy());
            }
            copy.put(entry.getKey(), newList);
        }
        return copy;
    }
}