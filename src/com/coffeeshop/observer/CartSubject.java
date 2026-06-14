package com.coffeeshop.observer;

import com.coffeeshop.command.CartUpdateCommand;
import com.coffeeshop.command.Command;
import com.coffeeshop.memento.OrderCaretaker;
import com.coffeeshop.memento.OrderMemento;
import com.coffeeshop.model.MenuItem;
import com.coffeeshop.model.TableItem;
import com.coffeeshop.util.StatusUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // Prototype Constructor
        public OrderItem(OrderItem other) {
            this.item = other.item.clone(); // ứng dụng prototype
            this.quantity = other.quantity;
            this.note = other.note;
        }

        // Ứng dụng prototype
        public OrderItem deepCopy() {
            return new OrderItem(this);
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

    // SỬ DỤNG CARETAKER THAY CHO STACK TRỰC TIẾP
    private final OrderCaretaker caretaker = new OrderCaretaker();

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

    public boolean hasOrder(int tableId) {
        List<OrderItem> cart = tableCarts.get(tableId);
        return cart != null && !cart.isEmpty();
    }

    public void refreshTableStatus(List<TableItem> tables) {
        for (TableItem table : tables) {
            if (hasOrder(table.getId())) {
                table.setStatus(StatusUtil.TABLE_BUSY);
            } else {
                table.setStatus(StatusUtil.TABLE_FREE);
            }
        }
    }

    // ==========================================
    // PHẦN TÍCH HỢP COMMAND & MEMENTO
    // ==========================================

    public OrderMemento createMemento() {
        return new OrderMemento(this.tableCarts);
    }

    public void restoreMemento(OrderMemento memento) {
        this.tableCarts = memento.getState();
        notifyObservers(); // Tự động cập nhật UI khi Undo/Redo
    }

    // Hàm tiện ích để bọc các logic thay đổi State vào Command
    private void executeAction(Runnable action) {
        Command cmd = new CartUpdateCommand(this, action);
        cmd.execute();
        caretaker.saveCommand(cmd);
    }

    public void undo() {
        caretaker.undo();
    }

    public void redo() {
        caretaker.redo();
    }

    // ==========================================
    // CÁC HÀM XỬ LÝ NGHIỆP VỤ (GIỮ NGUYÊN LOGIC)
    // ==========================================

    public List<OrderItem> getCartItems() {
        if (currentTableId == -1) {
            return new ArrayList<>();
        }
        return tableCarts.getOrDefault(currentTableId, new ArrayList<>());
    }

    public void clearCurrentCart() {
        if (currentTableId == -1) {
            return;
        }
        executeAction(() -> {
            tableCarts.put(currentTableId, new ArrayList<>());
            notifyObservers();
        });
    }

    public void removeOrderItem(OrderItem item) {
        if (currentTableId == -1) {
            return;
        }
        executeAction(() -> {
            List<OrderItem> cart = tableCarts.get(currentTableId);
            if (cart != null) {
                cart.remove(item);
                notifyObservers();
            }
        });
    }

    public void updateQuantityByItem(OrderItem item, int delta) {
        if (item == null) {
            return;
        }
        executeAction(() -> {
            item.setQuantity(item.getQuantity() + delta);
            if (item.getQuantity() <= 0) {
                List<OrderItem> cart = tableCarts.get(currentTableId);
                if (cart != null) {
                    cart.remove(item);
                }
            }
            notifyObservers();
        });
    }

    public boolean copyOrderToTable(int targetTableId) {
        if (currentTableId == -1 || currentTableId == targetTableId) {
            return false;
        }

        List<OrderItem> currentItems = tableCarts.get(currentTableId);
        if (currentItems == null || currentItems.isEmpty()) {
            return false;
        }

        executeAction(() -> {
            List<OrderItem> newItems = new ArrayList<>();
            for (OrderItem oi : currentItems) {
                newItems.add(oi.deepCopy());
            }
            tableCarts.put(targetTableId, newItems);
            notifyObservers();
        });

        return true;
    }

    public void addItem(MenuItem item) {
        addItem(item, 1, "");
    }

    public void addItem(MenuItem item, int quantity, String note) {
        if (currentTableId == -1 || item == null || quantity <= 0) {
            System.out.println("Vui lòng chọn bàn trước khi thêm món!");
            return;
        }

        executeAction(() -> {
            tableCarts.putIfAbsent(currentTableId, new ArrayList<>());
            List<OrderItem> cart = tableCarts.get(currentTableId);

            for (OrderItem order : cart) {
                if (order.getItem().getName().equals(item.getName())
                        && order.getNote().equals(note)
                        && order.getItem().getFinalPrice() == item.getFinalPrice()) {
                    order.setQuantity(order.getQuantity() + quantity);
                    notifyObservers();
                    return;
                }
            }
            cart.add(new OrderItem(item, quantity, note));
            notifyObservers();
        });
    }
}