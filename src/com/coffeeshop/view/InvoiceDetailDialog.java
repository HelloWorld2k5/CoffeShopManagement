package com.coffeeshop.view;
import com.coffeeshop.model.Invoice;
import com.coffeeshop.model.InvoiceItem;
import com.coffeeshop.util.MoneyUtil;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InvoiceDetailDialog extends JDialog {

    public InvoiceDetailDialog(JFrame parent, Invoice invoice) {
        super(parent, "Chi tiết hóa đơn: " + invoice.getInvoiceId(), true);
        setSize(400, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel();
        header.add(new JLabel("<html><center><b>QUÁN COFFEE TRÀ SỮA 175</b><br>Hóa đơn: " + invoice.getInvoiceId() + "</center></html>"));
        add(header, BorderLayout.NORTH);

        // Bảng danh sách món
        String[] cols = {"Tên món", "SL", "Đơn giá", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        for (InvoiceItem item : invoice.getItems()) {
            String displayDishName = item.getDishName();
            // Nếu có topping thì nối chuỗi vào
            if (item.getToppings() != null && !item.getToppings().isEmpty()) {
            displayDishName += " (" + item.getToppings() + ")";
            }
            model.addRow(new Object[]{
            displayDishName, // Tên món kèm topping
            item.getQuantityOrdered(),
            MoneyUtil.format(item.getPriceAtTime()),
            MoneyUtil.format(item.getTotalAmountAfterDiscount())
            });
        }
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Footer (Tổng tiền)
        JPanel footer = new JPanel(new GridLayout(3, 1));
        footer.add(new JLabel("Tổng cộng: " + String.format("%,.0f đ", invoice.getTotalAmount())));
        footer.add(new JLabel("Phương thức: " + invoice.getPaymentMethod()));
        
        JButton btnClose = new JButton("Đóng");
        btnClose.addActionListener(e -> dispose());
        footer.add(btnClose);
        
        add(footer, BorderLayout.SOUTH);
    }
}