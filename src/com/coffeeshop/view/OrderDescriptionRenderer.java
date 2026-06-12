package com.coffeeshop.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

public class OrderDescriptionRenderer extends JTextArea implements TableCellRenderer {
    public OrderDescriptionRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        // Sử dụng chuỗi định dạng HTML truyền vào từ TableModel để xuống hàng đẹp đẽ
        setText(value != null ? value.toString() : "");
        
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        
        // Tự động tính toán chiều cao dòng dựa trên nội dung Topping dài hay ngắn
        int width = table.getColumnModel().getColumn(column).getWidth();
        setSize(new Dimension(width, getPreferredSize().height));
        if (table.getRowHeight(row) != getPreferredSize().height + 10) {
            table.setRowHeight(row, Math.max(55, getPreferredSize().height + 10));
        }
        
        return this;
    }
}