package com.coffeeshop.util;

public final class AppConstants {
    private AppConstants() {
    } // Prevent instantiation

    // Định dạng hiển thị
    public static final String CURRENCY_FORMAT = "%,.0f đ";
    public static final String MONEY_PATTERN = "%,.0f";

    // Trạng thái
    public static final String STATUS_AVAILABLE = "AVAILABLE";
    public static final String STATUS_BUSY = "BUSY";
    public static final String STATUS_FREE = "FREE";
    public static final String STATUS_OUT_OF_STOCK = "OUT_OF_STOCK";
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";

    // Phương thức thanh toán
    public static final String PAYMENT_CASH = "CASH";
    public static final String PAYMENT_TRANSFER = "TRANSFER";
    public static final String PAYMENT_CARD = "CARD";

    // Loại bỏ magic number
}
