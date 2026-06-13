package com.coffeeshop.factory;

import com.coffeeshop.view.LoginFrame;
import java.awt.*;
import javax.swing.*;

public class StaffUIFactory implements UIFactory {

    @Override
    public JPanel createSidebar(com.coffeeshop.view.MainFrame mainFrame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Xếp dọc

        JButton btnMenu = createMenuButton("Thực đơn gọi món");
        btnMenu.addActionListener(e -> mainFrame.showPanel("MENU"));

        JButton btnHistory = createMenuButton("Lịch sử hóa đơn");
        btnHistory.addActionListener(e -> mainFrame.showPanel("HISTORY"));

        panel.add(btnMenu);
        panel.add(btnHistory);

        // Đẩy nút Đăng xuất xuống dưới cùng
        panel.add(Box.createVerticalGlue());

        JButton btnLogout = createMenuButton("Đăng xuất");
        btnLogout.setBackground(new Color(165, 42, 42)); // Màu đỏ cảnh báo
        // btnLogout.setForeground(Color.WHITE);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.addActionListener(e -> logout(mainFrame));
        panel.add(btnLogout);

        return panel;
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Chiếm full chiều rộng
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    private void logout(com.coffeeshop.view.MainFrame mainFrame) {
        int confirm = JOptionPane.showConfirmDialog(mainFrame,
                "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            mainFrame.dispose(); // Đóng cửa sổ hiện tại
            new LoginFrame().setVisible(true); // Mở lại form Login (Thay LoginFrame bằng tên class của bạn)
        }
    }

    @Override
    public String getDashboardTitle() {
        return "Coffee 175";
    }

    @Override
    public String getRole() {
        return "Nhân viên";
    }
}