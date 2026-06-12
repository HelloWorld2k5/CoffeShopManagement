package com.coffeeshop.view;

import com.coffeeshop.model.*;
import com.coffeeshop.repository.UserRepository;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

public class EmployeeManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private UserRepository userRepository = new UserRepository();

    public EmployeeManagementPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Khởi tạo HeaderPanel (Sửa lỗi thiếu khai báo)
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAddUser = new JButton("Thêm tài khoản");
        btnAddUser.setBackground(new Color(27, 163, 76));
        btnAddUser.setForeground(Color.WHITE);
        btnAddUser.setFocusPainted(false);
        btnAddUser.setOpaque(true);
        btnAddUser.setContentAreaFilled(true);
        btnAddUser.setBorderPainted(false);
        btnAddUser.setBorder(BorderFactory.createCompoundBorder(
        btnAddUser.getBorder(), 
        BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        btnAddUser.setFont(new Font("Arial", Font.BOLD, 14));
        btnAddUser.addActionListener(e -> showAddUserDialog());
        headerPanel.add(btnAddUser);
        add(headerPanel, BorderLayout.NORTH);

        // 2. Cấu hình bảng
        String[] cols = {"STT", "Họ tên", "Tài khoản", "Vai trò", "Trạng thái", "Hành động"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return column == 5; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(35); // Tăng chiều cao để nút bấm dễ nhấn hơn

        // 3. Đăng ký Renderer và Editor
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        add(new JScrollPane(table), BorderLayout.CENTER);
        loadData();
    }

    private void showAddUserDialog() {
        AddUserDialog dialog = new AddUserDialog((Frame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        if (dialog.isSaved()) {
            loadData();
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<User> users = userRepository.findAll();
        int stt = 1;
        for (User u : users) {
            tableModel.addRow(new Object[]{stt++, u.getFullName(), u.getUsername(), u.getUserRole(), u.getUserStatus(), ""});
        }
    }

    // --- CÁC HÀM XỬ LÝ ACTION ---
    private void handleEdit(int row) {
        String username = tableModel.getValueAt(row, 2).toString();
        User user = userRepository.findByUsername(username);
        EditUserDialog dialog = new EditUserDialog((Frame) SwingUtilities.getWindowAncestor(this), user);
        dialog.setVisible(true);
        if (dialog.isSaved()) loadData();
    }

    private void handleDelete(int row) {
        String username = tableModel.getValueAt(row, 2).toString();
        User user = userRepository.findByUsername(username);
        
        if (user.getUserRole() == UserRole.ADMIN) {
            JOptionPane.showMessageDialog(this, "Không thể xóa Admin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa " + user.getFullName() + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (userRepository.delete(user.getUserId())) {
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xóa!");
            }
        }
    }

    // --- INNER CLASS: RENDERER & EDITOR ---
    class ButtonRenderer extends DefaultTableCellRenderer {
        ActionPanel panel = new ActionPanel();
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return panel;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        ActionPanel panel = new ActionPanel();
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel.btnEdit.addActionListener(e -> {
                fireEditingStopped();
                handleEdit(currentRow);
            });
            panel.btnDelete.addActionListener(e -> {
                fireEditingStopped();
                handleDelete(currentRow);
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.currentRow = row;
            return panel;
        }
    }
}