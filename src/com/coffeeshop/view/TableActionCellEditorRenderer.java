package com.coffeeshop.view;

import com.coffeeshop.observer.CartSubject;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class TableActionCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private JPanel panel;
    private JButton btnMinus, btnPlus, btnDelete;
    private JLabel lblQty;
    private CartSubject cartSubject;
    private boolean isDeleteColumn;
    
    // Lưu trữ item hiện tại đang được tương tác trong bảng
    private Object currentOrderItem;

    public TableActionCellEditorRenderer(CartSubject cartSubject, boolean isDeleteColumn) {
        this.cartSubject = cartSubject;
        this.isDeleteColumn = isDeleteColumn;
        
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        panel.setOpaque(true);

        if (!isDeleteColumn) {
            btnMinus = new JButton("-");
            lblQty = new JLabel("1");
            btnPlus = new JButton("+");
            
            Font btnFont = new Font("Arial", Font.BOLD, 12);
            btnMinus.setFont(btnFont);
            btnPlus.setFont(btnFont);
            
            Insets zeroInsets = new Insets(0, 0, 0, 0);
            btnMinus.setMargin(zeroInsets);
            btnPlus.setMargin(zeroInsets);
            
            btnMinus.setFocusPainted(false);
            btnPlus.setFocusPainted(false);
            
            Dimension btnSize = new Dimension(24, 22);
            btnMinus.setPreferredSize(btnSize);
            btnPlus.setPreferredSize(btnSize);
            
            panel.add(btnMinus);
            panel.add(lblQty);
            panel.add(btnPlus);

            btnMinus.addActionListener(e -> { 
                if (currentOrderItem instanceof CartSubject.OrderItem) {
                    CartSubject.OrderItem oi = (CartSubject.OrderItem) currentOrderItem;
                    cartSubject.updateQuantityByItem(oi, -1);
                    fireEditingStopped(); // Cập nhật lại UI sau khi xử lý
                }
            });
            
            btnPlus.addActionListener(e -> { 
                if (currentOrderItem instanceof CartSubject.OrderItem) {
                    CartSubject.OrderItem oi = (CartSubject.OrderItem) currentOrderItem;
                    cartSubject.updateQuantityByItem(oi, 1);
                    fireEditingStopped(); // Cập nhật lại UI sau khi xử lý
                }
            });
        } else {
            btnDelete = new JButton("Xóa");
            btnDelete.setForeground(Color.RED);
            btnDelete.setFont(new Font("Arial", Font.PLAIN, 11));
            btnDelete.setFocusPainted(false);
            panel.add(btnDelete);
            
            btnDelete.addActionListener(e -> { 
                if (currentOrderItem instanceof CartSubject.OrderItem) {
                    CartSubject.OrderItem oi = (CartSubject.OrderItem) currentOrderItem;
                    cartSubject.removeOrderItem(oi);
                    fireEditingStopped(); // Cập nhật lại UI sau khi xử lý
                }
            });
        }
    }

    // --- Renderer: Dùng để vẽ giao diện bình thường ---
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        panel.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
        
        if (value instanceof CartSubject.OrderItem) {
            CartSubject.OrderItem oi = (CartSubject.OrderItem) value;
            if (!isDeleteColumn) {
                lblQty.setText(String.valueOf(oi.getQuantity()));
            }
        } else if (!isDeleteColumn) {
            lblQty.setText(value != null ? value.toString() : "1");
        }
        return panel;
    }

    // --- Editor: Dùng khi người dùng click vào ô để thao tác ---
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel.setBackground(table.getSelectionBackground());
        
        if (value instanceof CartSubject.OrderItem) {
            this.currentOrderItem = value;
            CartSubject.OrderItem oi = (CartSubject.OrderItem) value;
            if (!isDeleteColumn) {
                lblQty.setText(String.valueOf(oi.getQuantity()));
            }
        } else {
            this.currentOrderItem = null;
            if (!isDeleteColumn) {
                lblQty.setText(value != null ? value.toString() : "1");
            }
        }
        return panel;
    }

    @Override
    public Object getCellEditorValue() { 
        return currentOrderItem; 
    }
}