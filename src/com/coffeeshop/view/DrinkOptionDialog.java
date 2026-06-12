package com.coffeeshop.view;

import com.coffeeshop.decorator.*;
import com.coffeeshop.model.MenuItem;
import com.coffeeshop.observer.CartSubject; // THÊM IMPORT
import java.awt.*;
import javax.swing.*;

public class DrinkOptionDialog extends JDialog {
    private MenuItem baseItem;
    private MenuItem finalItem;
    private CartSubject cartSubject; // THÊM BIẾN LƯU TRỮ
    private JCheckBox chkExtraShot, chkCaramel, chkAlmondMilk;
    
    private final double PRICE_EXTRA_SHOT = 10000;
    private final double PRICE_CARAMEL = 8000;
    private final double PRICE_ALMOND = 12000;

    // CẬP NHẬT CONSTRUCTOR NHẬN THÊM CARTSUBJECT
    public DrinkOptionDialog(Frame parent, MenuItem item, CartSubject cartSubject) {
        super(parent, "Tùy chọn: " + item.getName(), true);
        this.baseItem = item;
        this.finalItem = item; 
        this.cartSubject = cartSubject; // GÁN GIÁ TRỊ
        
        initUI();
    }

    private void initUI() {
        // Tăng padding và khoảng cách cho UI gọn gàng hơn
        JPanel pnlMain = new JPanel(new GridLayout(0, 1, 8, 8));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        chkExtraShot = new JCheckBox("Extra Shot (+10.000đ)");
        chkCaramel = new JCheckBox("Caramel Syrup (+8.000đ)");
        chkAlmondMilk = new JCheckBox("Sữa Hạnh Nhân (+12.000đ)");
        
        JButton btnAdd = new JButton("Thêm vào giỏ");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 13));
        btnAdd.setBackground(new Color(46, 204, 113)); 
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setOpaque(true); 
        btnAdd.setBorderPainted(false); // Loại bỏ viền mặc định để button trông phẳng và đẹp hơn
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> applyDecorators());

        pnlMain.add(new JLabel("<html><b>Chọn thêm tùy chọn cho món:</b></html>"));
        pnlMain.add(chkExtraShot);
        pnlMain.add(chkCaramel);
        pnlMain.add(chkAlmondMilk);
        pnlMain.add(Box.createVerticalStrut(5));
        pnlMain.add(btnAdd);
        
        add(pnlMain);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void applyDecorators() {
        // Kiểm tra xem người dùng đã chọn bàn chưa trước khi thực hiện add món
        if (cartSubject.getCurrentTableId() == -1) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn một bàn ăn trước khi đặt món!", 
                "Cảnh báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        MenuItem tempItem = baseItem;
        
        if (chkExtraShot.isSelected()) {
            tempItem = new ExtraShotDecorator(tempItem, PRICE_EXTRA_SHOT);
        }
        if (chkCaramel.isSelected()) {
            tempItem = new CaramelSyrupDecorator(tempItem, PRICE_CARAMEL);
        }
        if (chkAlmondMilk.isSelected()) {
            tempItem = new AlmondMilkDecorator(tempItem, PRICE_ALMOND);
        }
        
        this.finalItem = tempItem;
        
        // CẬP NHẬT QUAN TRỌNG: Đẩy thẳng món kèm Decorator vào giỏ hàng của bàn đang chọn
        cartSubject.addItem(finalItem);
        
        dispose(); // Đóng hộp thoại
    }
}