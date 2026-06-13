// package com.coffeeshop.view;

// import com.coffeeshop.adapter.InvoicePrinter;
// import com.coffeeshop.adapter.TxtInvoiceAdapter;
// import com.coffeeshop.model.Invoice;
// import javax.swing.*;

// public class InvoicePreviewDialog extends JDialog {
//     private boolean isPrinted = false; // Biến cờ

//     public boolean isPrinted() { return isPrinted; }
//     private Invoice invoice;
    
//     private void printInvoice() {
//         try {
//             InvoicePrinter printer = new TxtInvoiceAdapter(); 
//             printer.print(invoice, "HoaDon_" + invoice.getInvoiceId() + ".txt");
            
//             this.isPrinted = true; // Đánh dấu thành công
//             JOptionPane.showMessageDialog(this, "Đã in hóa đơn!");
//             dispose();
//         } catch (Exception ex) {
//             JOptionPane.showMessageDialog(this, "Lỗi in: " + ex.getMessage());
//         }
//     }
// }