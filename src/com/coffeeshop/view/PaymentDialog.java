package com.coffeeshop.view;

import com.coffeeshop.adapter.InvoicePrinter;
import com.coffeeshop.adapter.TxtInvoiceAdapter;
import com.coffeeshop.model.Invoice;
import com.coffeeshop.service.PaymentService;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PaymentDialog extends JDialog {
    private JTextField txtCashIn = new JTextField(15);
    private JTextField txtChange = new JTextField(15);
    private JLabel lblTotalNet = new JLabel();
    private PaymentService paymentService = new PaymentService();
    private Invoice invoice;

    private boolean paymentSuccessful = false;
    public boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }

    private void onPrintInvoice() {
    // ... logic in hóa đơn của bạn ...
    this.paymentSuccessful = true; // Đánh dấu là đã thanh toán xong
    dispose();
    }

    public PaymentDialog(Frame parent, Invoice invoice) {
        super(parent, "Chi tiết thanh toán", true);
        this.invoice = invoice;
        setLayout(new BorderLayout());
        
        // --- UI Setup (Đơn giản hóa) ---
        // ... (Bạn code UI như screenshot ở đây) ...
        
        // Logic tự động tính tiền thối lại
        txtCashIn.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { updateChange(); }
            public void removeUpdate(DocumentEvent e) { updateChange(); }
            public void insertUpdate(DocumentEvent e) { updateChange(); }
        });

        // Nút Mệnh giá nhanh
        JButton btn50k = new JButton("50.000 đ");
        btn50k.addActionListener(e -> txtCashIn.setText("50000"));
        // ... thêm cho 100k, 200k, 500k ...

        // Nút Xác nhận & In
        JButton btnConfirm = new JButton("Xác nhận & In hóa đơn");
        btnConfirm.addActionListener(e -> proceedToPreview());
        add(btnConfirm, BorderLayout.SOUTH);
    }

    private void updateChange() {
        try {
            double cashIn = Double.parseDouble(txtCashIn.getText());
            double total = invoice.getTotalAmount();
            double change = cashIn - total;
            txtChange.setText(String.format("%,.0f đ", change));
        } catch (Exception ex) { txtChange.setText("0 đ"); }
    }

  // Trong PaymentDialog.java
private void proceedToPreview() {
    try {
        double cashIn = Double.parseDouble(txtCashIn.getText());
        // 1. Cập nhật hóa đơn
        paymentService.calculateInvoice(invoice, 0, cashIn); 
        
        // 2. In hóa đơn (Sử dụng Adapter Pattern bạn đã có)
        InvoicePrinter printer = new TxtInvoiceAdapter(); // Hoặc ExcelInvoiceAdapter
        String fileName = "HoaDon_" + invoice.getInvoiceId() + ".txt";
        printer.print(invoice, fileName);
        
        // 3. Đánh dấu thành công
        this.paymentSuccessful = true;
        JOptionPane.showMessageDialog(this, "Đã in hóa đơn!");
        dispose(); // Đóng dialog
        
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ!");
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Lỗi in hóa đơn: " + ex.getMessage());
    }
}

}