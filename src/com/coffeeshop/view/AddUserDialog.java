package com.coffeeshop.view;

import com.coffeeshop.model.User;
import com.coffeeshop.model.UserRole; // Import cụ thể
import com.coffeeshop.model.UserStatus; // Import cụ thể
import com.coffeeshop.repository.UserRepository;
import com.coffeeshop.util.PasswordUtil;

import java.awt.*;
import javax.swing.*;

public class AddUserDialog extends JDialog {
    private JTextField txtUsername = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);
    private JTextField txtFullName = new JTextField(20);

    // Sử dụng JComboBox đúng cách
    private JComboBox<UserRole> cbRole = new JComboBox<>(UserRole.values());
    private JComboBox<UserStatus> cbStatus = new JComboBox<>(UserStatus.values());

    private boolean saved = false;

    public AddUserDialog(Frame parent) {
        super(parent, "Đăng ký tài khoản", true);
        setLayout(new GridLayout(6, 2, 10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        add(new JLabel("Họ tên:"));
        add(txtFullName);
        add(new JLabel("Tài khoản:"));
        add(txtUsername);
        add(new JLabel("Mật khẩu:"));
        add(txtPassword);
        add(new JLabel("Vai trò:"));
        add(cbRole);
        add(new JLabel("Trạng thái:"));
        add(cbStatus);

        // Nút Lưu
        JButton btnSave = new JButton("Lưu");
        btnSave.addActionListener(e -> {
            if (txtUsername.getText().isEmpty() || txtFullName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
                return;
            }

            // 2. Tạo đối tượng User
            User newUser = new User();
            newUser.setUsername(txtUsername.getText());
            // Lưu ý: Nên băm mật khẩu trước khi lưu, ở đây tạm lưu text theo yêu cầu của
            // bạn
            newUser.setEncryptedPassword(PasswordUtil.sha256(String.valueOf(txtPassword.getPassword())));
            newUser.setFullName(txtFullName.getText());
            newUser.setUserRole((UserRole) cbRole.getSelectedItem());
            newUser.setUserStatus((UserStatus) cbStatus.getSelectedItem());

            // 3. Gọi Repository để insert
            UserRepository repo = new UserRepository();
            if (repo.insert(newUser)) {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
                saved = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi: Không thể thêm tài khoản vào cơ sở dữ liệu!");
            }
        });
        add(btnSave);

        // Nút Hủy (Sửa lỗi tại đây)
        JButton btnCancel = new JButton("Hủy");
        btnCancel.addActionListener(e -> dispose());
        add(btnCancel);
    }

    public boolean isSaved() {
        return saved;
    }
}