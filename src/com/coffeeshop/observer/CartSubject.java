package com.coffeeshop.observer;

import com.coffeeshop.model.MenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class CartSubject implements Subject {

    private final List<Observer> observers = new ArrayList<>();

    public static class OrderItem {

        private MenuItem item;
        private int quantity;
        private String note;

        public OrderItem(MenuItem item, int quantity, String note) {
            this.item = item;
            this.quantity = quantity;
            this.note = note;
        }

        public MenuItem getItem() {
            return item;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getNote() {
            return note;
        }
    }

    private Map<Integer, List<OrderItem>> tableCarts = new HashMap<>();
    private int currentTableId = -1;

    private final Stack<Map<Integer, List<OrderItem>>> undoStack = new Stack<>();
    private final Stack<Map<Integer, List<OrderItem>>> redoStack = new Stack<>();

    @Override
    public void registerObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void setCurrentTable(int tableId) {
        currentTableId = tableId;
        notifyObservers();
    }

    public int getCurrentTableId() {
        return currentTableId;
    }

    private void saveState() {
        undoStack.push(deepCopy(tableCarts));
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(deepCopy(tableCarts));
            tableCarts = undoStack.pop();
            notifyObservers();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(deepCopy(tableCarts));
            tableCarts = redoStack.pop();
            notifyObservers();
        }
    }

    private Map<Integer, List<OrderItem>> deepCopy(
            Map<Integer, List<OrderItem>> source) {

        Map<Integer, List<OrderItem>> copy = new HashMap<>();

        for (Integer key : source.keySet()) {

            List<OrderItem> newList = new ArrayList<>();

            for (OrderItem item : source.get(key)) {
                newList.add(
                        new OrderItem(
                                item.getItem(),
                                item.getQuantity(),
                                item.getNote()));
            }

            copy.put(key, newList);
        }

        return copy;
    }

    public List<OrderItem> getCartItems() {

        if (currentTableId == -1) {
            return new ArrayList<>();
        }

        return tableCarts.getOrDefault(
                currentTableId,
                new ArrayList<>());
    }

    public void clearCurrentCart() {

        if (currentTableId == -1) {
            return;
        }

        saveState();

        tableCarts.put(currentTableId, new ArrayList<>());

        notifyObservers();
    }

    public void removeOrderItem(OrderItem item) {

        if (currentTableId == -1) {
            return;
        }

        List<OrderItem> cart = tableCarts.get(currentTableId);

        if (cart != null) {

            saveState();

            cart.remove(item);

            notifyObservers();
        }
    }

    public void updateQuantityByItem(OrderItem item, int delta) {

        if (item == null) {
            return;
        }

        saveState();

        item.setQuantity(item.getQuantity() + delta);

        if (item.getQuantity() <= 0) {
            removeOrderItem(item);
        }

        notifyObservers();
    }

    // Thêm đoạn này vào trong lớp CartSubject.java
public boolean copyOrderToTable(int targetTableId) {
    if (currentTableId == -1 || currentTableId == targetTableId) {
        return false;
    }

    List<OrderItem> currentItems = tableCarts.get(currentTableId);
    if (currentItems == null || currentItems.isEmpty()) {
        return false;
    }

    // Sao chép sang bàn mới (Tạo danh sách mới để tránh tham chiếu chung)
    List<OrderItem> newItems = new ArrayList<>();
    for (OrderItem oi : currentItems) {
        // Tạo một OrderItem mới dựa trên thông tin cũ
        newItems.add(new OrderItem(oi.getItem(), oi.getQuantity(), oi.getNote()));
    }

    tableCarts.put(targetTableId, newItems);
    notifyObservers(); // Thông báo để cập nhật UI
    return true;
}




    public void addItem(MenuItem item) {
        addItem(item, 1, ""); 
    }

    // Phương thức chính
    public void addItem(MenuItem item, int quantity, String note) {
        if (currentTableId == -1 || item == null || quantity <= 0) {
            System.out.println("Vui lòng chọn bàn trước khi thêm món!");
            return;
        }

        saveState();
        tableCarts.putIfAbsent(currentTableId, new ArrayList<>());
        List<OrderItem> cart = tableCarts.get(currentTableId);

        // Kiểm tra xem món đã có trong giỏ chưa (với đúng cấu hình decorator)
        for (OrderItem order : cart) {
            if (order.getItem().getName().equals(item.getName())
                    && order.getNote().equals(note)
                    && order.getItem().getFinalPrice() == item.getFinalPrice()) {

                order.setQuantity(order.getQuantity() + quantity);
                notifyObservers();
                return;
            }
        }

        // Nếu chưa có, thêm mới
        cart.add(new OrderItem(item, quantity, note));
        notifyObservers();
    }    

}