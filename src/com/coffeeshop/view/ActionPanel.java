package com.coffeeshop.view;

import java.awt.*;
import javax.swing.*;

public class ActionPanel extends JPanel {
    public JButton btnEdit = new JButton("Sửa");
    public JButton btnDelete = new JButton("Xóa");

    public ActionPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        setOpaque(true);
        add(btnEdit);
        add(btnDelete);
    }
}