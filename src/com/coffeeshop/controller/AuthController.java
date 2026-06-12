package com.coffeeshop.controller;

import com.coffeeshop.model.User;
import com.coffeeshop.service.AuthService;

public class AuthController {
    private final AuthService authService = new AuthService();

    public User handleLogin(String username, String password) {
    try {
        return authService.login(username, password);
    } catch (Exception e) {
        // Ghi log để lập trình viên kiểm tra
        System.err.println("Lỗi hệ thống khi đăng nhập: " + e.getMessage());
        // Trả về null hoặc ném một custom exception tùy ý
        return null;
    }
}

    public void handleLogout() {
        authService.logout();
    }
}