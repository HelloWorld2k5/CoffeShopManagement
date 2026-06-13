package com.coffeeshop.view;

import com.coffeeshop.factory.UIFactory;
import com.coffeeshop.observer.CartSubject;
import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private UIFactory uiFactory;
    private JPanel contentPanel;

    // Khởi tạo CartSubject dùng chung cho toàn bộ phiên đăng nhập này
    private final CartSubject cartSubject = new CartSubject();
    // private InvoiceHistoryPanel invoiceHistoryPanel;

    private InvoiceHistoryPanel invoiceHistoryPanel;

    public MainFrame(UIFactory factory) {
        this.uiFactory = factory;

        setTitle(uiFactory.getDashboardTitle());
        setSize(1200, 750); // Tăng nhẹ chiều rộng lên 1200px để giao diện giỏ hàng bên phải không bị chật
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        // 1. Sidebar (Được tạo bởi Factory - nằm bên trái)
        JPanel sidebar = uiFactory.createSidebar(this);
        sidebar.setPreferredSize(new Dimension(200, 0));
        add(sidebar, BorderLayout.WEST);

        // 2. Content Area (Sử dụng CardLayout để chuyển đổi giữa các chức năng)
        contentPanel = new JPanel(new CardLayout());
        // Khởi tạo màn hình bán hàng/gọi món tích hợp giỏ hàng
        JPanel saleViewPanel = createSaleViewPanel();
        // Nạp màn hình vào CardLayout với định danh
        contentPanel.add(saleViewPanel, "MENU");
        // THÊM CÁC DÒNG NÀY ĐỂ ĐĂNG KÝ PANEL
        invoiceHistoryPanel = new InvoiceHistoryPanel();
        contentPanel.add(invoiceHistoryPanel, "HISTORY");
        contentPanel.add(new EmployeeManagementPanel(), "STAFF");
        add(contentPanel, BorderLayout.CENTER);

        // 3. Status Bar ở dưới cùng
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        statusBar.add(new JLabel("Quyền hạn: " + uiFactory.getRole()));
        add(statusBar, BorderLayout.SOUTH);

        // invoiceHistoryPanel = new InvoiceHistoryPanel();
    }

    public InvoiceHistoryPanel getInvoiceHistoryPanel() {
        return invoiceHistoryPanel;
    }

    /**
     * Hàm helper tạo ra giao diện Bán hàng/Gọi món tiêu chuẩn:
     * Gồm thực đơn ở giữa (CENTER) và Giỏ hàng chọn bàn bên phải (EAST)
     */
    private JPanel createSaleViewPanel() {
        JPanel salePanel = new JPanel(new BorderLayout(10, 10));
        salePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Tầng giữa: Danh sách món ăn nạp qua JScrollPane để có thanh cuộn khi thực đơn
        // dài
        // Lưu ý: Cần chỉnh sửa MenuPanel nhận cartSubject nếu bạn muốn click nút là add
        // thẳng vào giỏ
        MenuPanel menuPanel = new MenuPanel(cartSubject);
        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setBorder(BorderFactory.createTitledBorder("THỰC ĐƠN"));
        salePanel.add(menuScrollPane, BorderLayout.CENTER);

        // Tầng phải: Thanh giỏ hàng & Tính tiền (Định kích cỡ cố định chiều rộng là
        // 350px)
        CartPanel cartPanel = new CartPanel(cartSubject);
        cartPanel.setPreferredSize(new Dimension(360, 0));
        cartPanel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN ĐẶT MÓN"));
        salePanel.add(cartPanel, BorderLayout.EAST);

        return salePanel;
    }

    // Getter hỗ trợ việc chuyển đổi giao diện (CardLayout) từ Sidebar nếu cần
    public JPanel getContentPanel() {
        return contentPanel;
    }

    // Getter lấy dữ liệu giỏ hàng tập trung
    public CartSubject getCartSubject() {
        return cartSubject;
    }

    public void showPanel(String name) {
        // Nếu cố truy cập Staff nhưng không phải Admin (Giả sử bạn check qua UI
        // Factory)
        if ("STAFF".equals(name) && !"Admin-Quản lý".equals(uiFactory.getRole())) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập chức năng này!");
            return;
        }

        CardLayout cl = (CardLayout) (contentPanel.getLayout());
        cl.show(contentPanel, name);
    }
}