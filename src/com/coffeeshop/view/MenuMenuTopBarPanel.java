package com.coffeeshop.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MenuMenuTopBarPanel extends JPanel {
    private JTextField txtSearch;
    private JButton btnSearch;
    private JRadioButton rdoAll, rdoCoffee, rdoBakery, rdoCombo;
    private ButtonGroup filterGroup;
    private JButton btnAddNewItem;

    public MenuMenuTopBarPanel() {
        initUI();
    }

    private void initUI() {
        // Thiết kế Layout dạng GridBagLayout giúp các thành phần thẳng hàng, co giãn đẹp
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 5); // Khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.VERTICAL;

        // --- HÀNG 1: Ô TÌM KIẾM MÓN ĂN ---
        JLabel lblSearch = new JLabel("Tìm kiếm món: ");
        lblSearch.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        add(lblSearch, gbc);

        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 28)); // Chiều cao chuẩn 28px bằng ô chọn bàn
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0; // Co giãn để lấp khoảng trống ở giữa
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(txtSearch, gbc);

        btnSearch = new JButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(65, 28));
        btnSearch.setFocusPainted(false);
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(btnSearch, gbc);

        // Nút "THÊM MÓN" mới - Đẩy bám sát rìa bên phải bằng phương thức căn lưới
        btnAddNewItem = new JButton("+ THÊM MÓN");
        btnAddNewItem.setBackground(new Color(40, 165, 90)); // Màu xanh lá tươi tắn
        btnAddNewItem.setForeground(Color.WHITE);
        btnAddNewItem.setFont(new Font("Arial", Font.BOLD, 11));
        btnAddNewItem.setFocusPainted(false);
        btnAddNewItem.setPreferredSize(new Dimension(110, 28));
        gbc.gridx = 3; gbc.gridy = 0; gbc.weightx = 0;
        gbc.insets = new Insets(0, 15, 0, 0); // Tạo khoảng đệm cách biệt với ô tìm kiếm
        add(btnAddNewItem, gbc);

        // --- HÀNG 2: BỘ LỌC THEO DANH MỤC MÓN (DANHMUCMONAN) ---
        JPanel pnlFilters = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlFilters.setBackground(Color.WHITE);
        pnlFilters.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)), 
                "Danh mục thực đơn", 0, 0, new Font("Arial", Font.ITALIC, 11)
        ));

        rdoAll = new JRadioButton("Tất cả", true);
        rdoCoffee = new JRadioButton("Coffee");
        rdoBakery = new JRadioButton("Bánh ngọt");
        rdoCombo = new JRadioButton("Combo");

        // Đồng bộ màu nền Trắng cho đồng bộ UI tổng thể
        rdoAll.setBackground(Color.WHITE);
        rdoCoffee.setBackground(Color.WHITE);
        rdoBakery.setBackground(Color.WHITE);
        rdoCombo.setBackground(Color.WHITE);

        // Nhóm các nút lại với nhau để tạo hiệu ứng chọn duy nhất (Single Select)
        filterGroup = new ButtonGroup();
        filterGroup.add(rdoAll);
        filterGroup.add(rdoCoffee);
        filterGroup.add(rdoBakery);
        filterGroup.add(rdoCombo);

        pnlFilters.add(rdoAll);
        pnlFilters.add(rdoCoffee);
        pnlFilters.add(rdoBakery);
        pnlFilters.add(rdoCombo);

        // Đưa panel bộ lọc xuống hàng thứ 2 (gridy = 1)
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 4; // Trải dài qua 4 cột để tối ưu không gian hiển thị
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 0, 0); // Khoảng cách cách hàng tìm kiếm phía trên
        add(pnlFilters, gbc);

        // --- ĐOẠN XỬ LÝ SỰ KIỆN LOGIC (LISTENERS) ---
        btnAddNewItem.addActionListener(e -> openAddNewItemDialog());
        
        // Cài đặt bộ lọc dữ liệu khi click đổi nút danh mục
        rdoAll.addActionListener(e -> applyFilter("ALL"));
        rdoCoffee.addActionListener(e -> applyFilter("COFFEE"));
        rdoBakery.addActionListener(e -> applyFilter("BAKERY"));
        rdoCombo.addActionListener(e -> applyFilter("COMBO"));
        
        // Nhấn Enter ở ô tìm kiếm để thực hiện lọc ngay
        txtSearch.addActionListener(e -> applyFilter("SEARCH"));
        btnSearch.addActionListener(e -> applyFilter("SEARCH"));
    }

    private void applyFilter(String filterType) {
        String keyword = txtSearch.getText().trim();
        System.out.println("Đang thực hiện lọc: loại=" + filterType + " | Từ khóa=" + keyword);
        // TODO: Kết nối gọi hàm xử lý từ Controller/Database của bạn để render lại danh sách món ăn
        // Ví dụ: menuController.filterMenu(filterType, keyword);
    }

    private void openAddNewItemDialog() {
        // Thiết kế khung cửa sổ nhanh (JDialog) để nhập thông tin món ăn khớp trường SQL của bạn
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm Món Ăn Mới", true);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));
        ((JPanel)dialog.getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextField txtName = new JTextField();
        JComboBox<String> cbCategory = new JComboBox<>(new String[]{"COFFEE_MACHINE", "COFFEE_TRADITIONAL", "BAKERY", "COMBO"});
        JTextField txtPrice = new JTextField("0");
        JTextField txtIcon = new JTextField("☕");
        JTextField txtDesc = new JTextField();

        dialog.add(new JLabel("Tên món ăn (*):")); dialog.add(txtName);
        dialog.add(new JLabel("Danh mục món:")); dialog.add(cbCategory);
        dialog.add(new JLabel("Giá cơ bản (đ):")); dialog.add(txtPrice);
        dialog.add(new JLabel("Biểu tượng (Icon):")); dialog.add(txtIcon);
        dialog.add(new JLabel("Mô tả ngắn:")); dialog.add(txtDesc);

        JButton btnSave = new JButton("Lưu lại");
        btnSave.setBackground(new Color(40, 165, 90));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> {
            if(txtName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập tên món ăn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // TODO: Viết câu lệnh INSERT INTO monAn... gọi từ DAO thực thi lưu DB xuống MySQL tại đây
            JOptionPane.showMessageDialog(dialog, "Đã lưu món '" + txtName.getText() + "' vào CSDL thành công!");
            dialog.dispose();
        });
        
        dialog.add(new JLabel()); // Ô trống giữ khoảng cách
        dialog.add(btnSave);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}