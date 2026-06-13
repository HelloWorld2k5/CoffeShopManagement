package com.coffeeshop.adapter;

import com.coffeeshop.adapter.legacy.LegacyExcelWriter;
import com.coffeeshop.model.Invoice;
import com.coffeeshop.model.InvoiceItem;

import java.util.ArrayList;
import java.util.List;

public class ExcelInvoiceAdapter implements InvoicePrinter {

    private final LegacyExcelWriter writer = new LegacyExcelWriter();

    @Override
    public void print(Invoice invoice, String outputPath) throws Exception {
        List<List<Object>> rows = new ArrayList<>();

        rows.add(List.of("HOA DON BAN HANG", invoice.getInvoiceId()));
        rows.add(List.of("Ma ban", invoice.getTableId() == null ? "N/A" : invoice.getTableId()));
        rows.add(List.of("Ma nhan vien", invoice.getUserId() == null ? "N/A" : invoice.getUserId()));
        rows.add(List.of("Phuong thuc", invoice.getPaymentMethod()));
        rows.add(List.of("Thoi gian", invoice.getCreatedAt()));
        rows.add(List.of(""));

        rows.add(List.of("Ten mon", "So luong", "Don gia", "Thanh tien"));

        for (InvoiceItem item : invoice.getItems()) {
            rows.add(List.of(
                    item.getDishName(),
                    item.getQuantityOrdered(),
                    item.getPriceAtTime(),
                    item.getTotalAmountAfterDiscount()
            ));
        }

        rows.add(List.of(""));
        rows.add(List.of("Tong truoc thue", invoice.getSubtotal()));
        rows.add(List.of("VAT", invoice.getVatAmount()));
        rows.add(List.of("Giam gia", invoice.getDiscountAmount()));
        rows.add(List.of("Thanh tien", invoice.getTotalAmount()));
        rows.add(List.of("Tien khach dua", invoice.getAmountPaid()));
        rows.add(List.of("Tien tra lai", invoice.getChangeAmount()));

        writer.write(rows, outputPath);
    }
}