package com.coffeeshop.adapter;

import com.coffeeshop.adapter.legacy.LegacyTxtWriter;
import com.coffeeshop.model.Invoice;
import com.coffeeshop.model.InvoiceItem;
import com.coffeeshop.util.MoneyUtil;

public class TxtInvoiceAdapter implements InvoicePrinter {

    private final LegacyTxtWriter writer = new LegacyTxtWriter();

    @Override
    public void print(Invoice invoice, String outputPath) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("HOA DON BAN HANG\n");
        sb.append("Ma HD: ").append(invoice.getInvoiceId()).append("\n");
        sb.append("Ma ban: ").append(invoice.getTableId() == null ? "N/A" : invoice.getTableId()).append("\n");
        sb.append("Ma nhan vien: ").append(invoice.getUserId() == null ? "N/A" : invoice.getUserId()).append("\n");
        sb.append("Phuong thuc: ").append(invoice.getPaymentMethod()).append("\n");
        sb.append("Thoi gian: ").append(invoice.getCreatedAt()).append("\n");
        sb.append("----------------------------------------\n");

        for (InvoiceItem item : invoice.getItems()) {
            sb.append(item.getDishName())
                    .append(" x").append(item.getQuantityOrdered())
                    .append(" = ").append(MoneyUtil.format(item.getTotalAmountAfterDiscount()))
                    .append("\n");
        }

        sb.append("----------------------------------------\n");
        sb.append("Tong truoc thue: ").append(MoneyUtil.format(invoice.getSubtotal())).append("\n");
        sb.append("VAT: ").append(MoneyUtil.format(invoice.getVatAmount())).append("\n");
        sb.append("Giam gia: ").append(MoneyUtil.format(invoice.getDiscountAmount())).append("\n");
        sb.append("Thanh tien: ").append(MoneyUtil.format(invoice.getTotalAmount())).append("\n");
        sb.append("Tien khach dua: ").append(MoneyUtil.format(invoice.getAmountPaid())).append("\n");
        sb.append("Tien tra lai: ").append(MoneyUtil.format(invoice.getChangeAmount())).append("\n");

        writer.write(sb.toString(), outputPath);
    }
}