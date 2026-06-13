package com.coffeeshop.util;

public final class StatusUtil {
    private StatusUtil() {
    }

    // Trạng thái món ăn
    public static final String ITEM_AVAILABLE = "AVAILABLE";
    public static final String ITEM_OUT_OF_STOCK = "OUT_OF_STOCK";
    public static final String ITEM_DELETED = "DELETED";

    // Trạng thái bàn
    public static final String TABLE_FREE = "FREE";
    public static final String TABLE_BUSY = "BUSY";

    // Trạng thái người dùng
    public static final String USER_ACTIVE = "ACTIVE";
    public static final String USER_INACTIVE = "INACTIVE";

    // Trạng thái hóa đơn
    public static final String INVOICE_PENDING = "PENDING";
    public static final String INVOICE_PAID = "PAID";
    public static final String INVOICE_CANCELLED = "CANCELLED";

    // Trạng thái tùy chọn
    public static final String OPTION_AVAILABLE = "AVAILABLE";
    public static final String OPTION_OUT_OF_STOCK = "OUT_OF_STOCK";

    public static boolean isItemAvailable(String status) {
        return ITEM_AVAILABLE.equalsIgnoreCase(status);
    }

    public static boolean isTableFree(String status) {
        return TABLE_FREE.equalsIgnoreCase(status);
    }
}
