package com.coffeeshop.view;

import com.coffeeshop.adapter.ExcelInvoiceAdapter;
import com.coffeeshop.adapter.InvoicePrinter;
import com.coffeeshop.adapter.TxtInvoiceAdapter;
import com.coffeeshop.model.Invoice;
import com.coffeeshop.model.PaymentMethod;
import com.coffeeshop.service.PaymentService;
import com.coffeeshop.util.MoneyUtil;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PaymentDialog extends JDialog {
    private final JTextField txtCashIn = new JTextField(15);
    private final JTextField txtChange = new JTextField(15);

    // THAY ĐỔI 1: thêm các label hiển thị tổng tiền rõ ràng
    private final JLabel lblSubtotal = new JLabel();
    private final JLabel lblVat = new JLabel();
    private final JLabel lblDiscount = new JLabel();
    private final JLabel lblGrandTotal = new JLabel();

    // THAY ĐỔI 2: dùng enum thay vì nhập chuỗi
    private final JComboBox<PaymentMethod> cboPaymentMethod = new JComboBox<>(PaymentMethod.values());
    private final JComboBox<String> cboPrintFormat = new JComboBox<>(new String[] { "TXT", "EXCEL" });

    private final PaymentService paymentService = new PaymentService();
    private final Invoice invoice;

    private boolean paymentSuccessful = false;

    public boolean isPaymentSuccessful() {
        return paymentSuccessful;
    }

    public PaymentDialog(Frame parent, Invoice invoice) {
        super(parent, "Chi tiết thanh toán", true);
        this.invoice = invoice;

        // invoice.setInvoiceId(InvoiceIdGenerator.generate());

        setSize(700, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // ===== TOP =====
        JPanel topPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        topPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        topPanel.add(new JLabel("Tạm tính:"));
        lblSubtotal.setText(MoneyUtil.format(invoice.getSubtotal()));
        topPanel.add(lblSubtotal);

        topPanel.add(new JLabel("VAT:"));
        lblVat.setText(MoneyUtil.format(invoice.getVatAmount()));
        topPanel.add(lblVat);

        topPanel.add(new JLabel("Giảm giá:"));
        lblDiscount.setText(MoneyUtil.format(invoice.getDiscountAmount()));
        topPanel.add(lblDiscount);

        topPanel.add(new JLabel("Tổng thanh toán:"));
        lblGrandTotal.setText(MoneyUtil.format(invoice.getTotalAmount()));
        topPanel.add(lblGrandTotal);

        add(topPanel, BorderLayout.NORTH);

        // ===== CENTER =====
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Thanh toán"));

        centerPanel.add(new JLabel("Phương thức thanh toán:"));
        centerPanel.add(cboPaymentMethod);

        centerPanel.add(new JLabel("Tiền khách đưa:"));
        centerPanel.add(txtCashIn);

        centerPanel.add(new JLabel("Tiền trả lại:"));
        txtChange.setEditable(false);
        centerPanel.add(txtChange);

        centerPanel.add(new JLabel("Định dạng in:"));
        centerPanel.add(cboPrintFormat);

        add(centerPanel, BorderLayout.CENTER);

        // ===== QUICK MONEY BUTTONS =====
        JPanel quickPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        quickPanel.setBorder(BorderFactory.createTitledBorder("Mệnh giá nhanh"));

        JButton btn50k = new JButton("50.000 đ");
        btn50k.addActionListener(e -> txtCashIn.setText("50000"));

        JButton btn100k = new JButton("100.000 đ");
        btn100k.addActionListener(e -> txtCashIn.setText("100000"));

        JButton btn200k = new JButton("200.000 đ");
        btn200k.addActionListener(e -> txtCashIn.setText("200000"));

        JButton btn500k = new JButton("500.000 đ");
        btn500k.addActionListener(e -> txtCashIn.setText("500000"));

        quickPanel.add(btn50k);
        quickPanel.add(btn100k);
        quickPanel.add(btn200k);
        quickPanel.add(btn500k);

        add(quickPanel, BorderLayout.WEST);

        // ===== BOTTOM =====
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));

        JButton btnPreview = new JButton("Xác nhận & In hóa đơn");
        btnPreview.addActionListener(e -> proceedToPreview());

        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dispose());

        bottomPanel.add(btnCancel);
        bottomPanel.add(btnPreview);

        add(bottomPanel, BorderLayout.SOUTH);

        // THAY ĐỔI 3: tự động tính tiền thối
        txtCashIn.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateChange();
            }
        });

        updateChange();
    }

    private InvoicePrinter getSelectedPrinter() {
        String format = (String) cboPrintFormat.getSelectedItem();

        if ("EXCEL".equalsIgnoreCase(format)) {
            return new ExcelInvoiceAdapter();
        }

        return new TxtInvoiceAdapter();
    }

    // THAY ĐỔI 4: tính tiền thối, báo chưa đủ tiền nếu âm
    private void updateChange() {
        try {

            if (txtCashIn.getText().trim().isEmpty()) {
                txtChange.setText("");
                return;
            }

            double cashIn = Double.parseDouble(txtCashIn.getText().trim());

            double total = invoice.getTotalAmount();

            double change = cashIn - total;

            if (change < 0) {

                txtChange.setText(
                        "Thiếu "
                                + MoneyUtil.format(Math.abs(change)));

            } else {

                txtChange.setText(
                        MoneyUtil.format(change));
            }

        } catch (NumberFormatException ex) {

            txtChange.setText("Không hợp lệ");
        }
    }

    // THAY ĐỔI 5: xử lý thanh toán hoàn chỉnh
    private void proceedToPreview() {
        try {
            double cashIn = Double.parseDouble(txtCashIn.getText().trim());

            if (cashIn < invoice.getTotalAmount()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Khách đưa chưa đủ tiền!");
                return;
            }

            PaymentMethod method = (PaymentMethod) cboPaymentMethod.getSelectedItem();
            if (method == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phương thức thanh toán!");
                return;
            }

            invoice.setPaymentMethod(method);

            // Tính lại invoice theo logic service
            invoice.setAmountPaid(cashIn);

            invoice.setChangeAmount(
                    cashIn - invoice.getTotalAmount());

            // Lưu DB trước
            boolean saved = paymentService.saveInvoice(invoice);
            if (!saved) {
                JOptionPane.showMessageDialog(this, "Không thể lưu hóa đơn!");
                return;
            }

            // Xuất file TXT
            InvoicePrinter printer = getSelectedPrinter();

            String format = (String) cboPrintFormat.getSelectedItem();
            String extension = "EXCEL".equalsIgnoreCase(format) ? ".xlsx" : ".txt";

            String fileName = "HoaDon_" + invoice.getInvoiceId() + extension;
            printer.print(invoice, fileName);

            paymentSuccessful = true;

            JOptionPane.showMessageDialog(
                    this,
                    "Thanh toán thành công!\n"
                            + "Đã in file: " + fileName + "\n"
                            + "Tiền trả lại: " + MoneyUtil.format(invoice.getChangeAmount()));

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tiền khách đưa không hợp lệ!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi in hóa đơn: " + ex.getMessage());
        }
    }
}