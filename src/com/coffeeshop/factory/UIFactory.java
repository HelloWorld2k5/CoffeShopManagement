package com.coffeeshop.factory;

import javax.swing.*;

public interface UIFactory {
    // Truyền MainFrame vào để nút bấm có thể gọi lệnh chuyển màn hình/logout
    JPanel createSidebar(com.coffeeshop.view.MainFrame mainFrame); 
    String getDashboardTitle();
    String getRole();
}