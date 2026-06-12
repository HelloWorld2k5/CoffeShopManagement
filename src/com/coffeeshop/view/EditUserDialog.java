package com.coffeeshop.view;

import com.coffeeshop.model.User;
import com.coffeeshop.model.UserRole;
import com.coffeeshop.model.UserStatus;
import com.coffeeshop.repository.UserRepository;
import java.awt.*;
import javax.swing.*;

public class EditUserDialog extends JDialog {
    private JTextField txtUsername = new JTextField(20);
    private JTextField txtFullName = new JTextField(20);
    // Thêm trường mật khẩu mới (để trống nếu không muốn đổi)
    private JPasswordField txtNewPassword = new JPasswordField(20); 
    private JComboBox<UserRole> cbRole = new JComboBox<>(UserRole.values());
    private JComboBox<UserStatus> cbStatus = new JComboBox<>(UserStatus.values());
    private boolean saved = false;

    public EditUserDialog(Frame parent, User user) {
        super(parent, "Sửa tài khoản", true);
        setLayout(new GridLayout(6, 2, 10, 10)); // Tăng lên 6 dòng để thêm pass
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Nạp dữ liệu cũ
        txtFullName.setText(user.getFullName());
        txtUsername.setText(user.getUsername());
        txtUsername.setEditable(true);
        cbRole.setSelectedItem(user.getUserRole());
        cbStatus.setSelectedItem(user.getUserStatus());

        add(new JLabel("Họ tên:")); add(txtFullName);
        add(new JLabel("Tài khoản:")); add(txtUsername);
        add(new JLabel("Mật khẩu mới (để trống nếu không đổi):")); add(txtNewPassword);
        add(new JLabel("Vai trò:")); add(cbRole);
        add(new JLabel("Trạng thái:")); add(cbStatus);

        JButton btnSave = new JButton("Lưu thay đổi");
        btnSave.addActionListener(e -> {
            // 1. Cập nhật thông tin cơ bản
            user.setFullName(txtFullName.getText());
            user.setUserRole((UserRole) cbRole.getSelectedItem());
            user.setUserStatus((UserStatus) cbStatus.getSelectedItem());
            // 2. Xử lý mật khẩu
            String newPass = new String(txtNewPassword.getPassword());
            if (!newPass.isEmpty()) {
                user.setEncryptedPassword(newPass); 
            }
            
            // 3. Gửi lên DB
            UserRepository repo = new UserRepository();
            if (repo.update(user)) {
                saved = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi cập nhật!");
            }
        });
        add(btnSave);
        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dispose());
        add(btnCancel);
    }

    public boolean isSaved() { return saved; }
}