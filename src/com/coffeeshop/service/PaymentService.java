package com.coffeeshop.service;

import com.coffeeshop.config.AppConfig;
import com.coffeeshop.model.Invoice;
import com.coffeeshop.repository.InvoiceRepository;

public class PaymentService {

    // THAY ĐỔI 1: thêm repository để lưu hóa đơn
    private final InvoiceRepository invoiceRepository = new InvoiceRepository();

    /**
     * Validate invoice data before calculation
     */
    private void validateInvoice(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null");
        }
        if (invoice.getItems() == null || invoice.getItems().isEmpty()) {
            throw new IllegalArgumentException("Invoice must have at least one item");
        }
        if (invoice.getPaymentMethod() == null) {
            throw new IllegalArgumentException("Payment method is required");
        }
    }

    /**
     * Validate payment amounts
     */
    private void validatePaymentAmounts(double subtotal, double discount, double cashReceived) {
        if (subtotal < 0) {
            throw new IllegalArgumentException("Subtotal cannot be negative");
        }
        if (discount < 0 || discount > subtotal) {
            throw new IllegalArgumentException("Discount must be between 0 and subtotal");
        }
        if (cashReceived <= 0) {
            throw new IllegalArgumentException("Cash received must be greater than 0");
        }
    }

    // THAY ĐỔI 2: tách hàm kiểm tra tiền đủ
    private void validateEnoughCash(double total, double cashReceived) {
        if (cashReceived < total) {
            throw new IllegalArgumentException("Khách đưa chưa đủ tiền");
        }
    }

    public void calculateInvoice(Invoice invoice, double discount, double cashReceived) {
        validateInvoice(invoice);

        double vatRate = AppConfig.getInstance().getVatRate();

        invoice.setDiscountAmount(discount);
        invoice.setAmountPaid(cashReceived);

        double subtotal = invoice.getItems()
                .stream()
                .mapToDouble(item -> item.getTotalAmountAfterDiscount())
                .sum();

        validatePaymentAmounts(subtotal, discount, cashReceived);

        invoice.setSubtotal(subtotal);
        invoice.setVatAmount(subtotal * vatRate);

        double total = subtotal + invoice.getVatAmount() - discount;

        // THAY ĐỔI 3: không cho lưu tiền thối âm
        validateEnoughCash(total, cashReceived);

        invoice.setTotalAmount(total);
        invoice.setChangeAmount(cashReceived - total);
    }

    /**
     * Calculate invoice with custom VAT rate (override from config)
     */
    public void calculateInvoice(Invoice invoice, double vatRate, double discount, double cashReceived) {
        validateInvoice(invoice);

        if (vatRate < 0 || vatRate > 1) {
            throw new IllegalArgumentException("VAT rate must be between 0 and 1");
        }

        invoice.setDiscountAmount(discount);
        invoice.setAmountPaid(cashReceived);

        double subtotal = invoice.getItems()
                .stream()
                .mapToDouble(item -> item.getTotalAmountAfterDiscount())
                .sum();

        validatePaymentAmounts(subtotal, discount, cashReceived);

        invoice.setSubtotal(subtotal);
        invoice.setVatAmount(subtotal * vatRate);

        double total = subtotal + invoice.getVatAmount() - discount;

        // THAY ĐỔI 4: kiểm tra đủ tiền ở đây luôn
        validateEnoughCash(total, cashReceived);

        invoice.setTotalAmount(total);
        invoice.setChangeAmount(cashReceived - total);
    }

    // THAY ĐỔI 5: thêm hàm lưu hóa đơn
    public boolean saveInvoice(Invoice invoice) {

        validateInvoice(invoice);

        if (invoice.getPaymentMethod() == null) {
            throw new IllegalArgumentException(
                    "Vui lòng chọn phương thức thanh toán");
        }

        return invoiceRepository.save(invoice);
    }

    public void prepareInvoice(Invoice invoice, double discount) {

        validateInvoiceForPreview(invoice);

        double vatRate = AppConfig.getInstance().getVatRate();

        double subtotal = invoice.getItems()
                .stream()
                .mapToDouble(item -> item.getTotalAmountAfterDiscount())
                .sum();

        invoice.setSubtotal(subtotal);

        invoice.setDiscountAmount(discount);

        invoice.setVatAmount(subtotal * vatRate);

        invoice.setTotalAmount(
                subtotal
                        + invoice.getVatAmount()
                        - discount);
    }

    private void validateInvoiceForPreview(Invoice invoice) {

        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null");
        }

        if (invoice.getItems() == null
                || invoice.getItems().isEmpty()) {

            throw new IllegalArgumentException(
                    "Invoice must have at least one item");
        }
    }
}