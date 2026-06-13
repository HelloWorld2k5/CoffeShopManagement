package com.coffeeshop.util;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyUtil {
    private static final NumberFormat VN = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
    private static final String DEFAULT_SUFFIX = " đ";

    /** Format đầy đủ: "45.000 đ" (theo locale Việt Nam) */
    public static String format(double amount) {
        return VN.format(amount);
    }

    /** Format không kèm ký hiệu tiền tệ: "45.000" */
    public static String formatNumber(double amount) {
        return String.format("%,.0f", amount);
    }

    /** Format với suffix tùy chỉnh: "45.000đ" */
    public static String formatWithSuffix(double amount) {
        return String.format("%,.0f", amount) + DEFAULT_SUFFIX;
    }

    /** Parse chuỗi tiền về double (bỏ ký tự không phải số) */
    public static double parse(String moneyString) {
        if (moneyString == null || moneyString.isEmpty())
            return 0.0;
        return Double.parseDouble(moneyString.replaceAll("[^\\d.-]", ""));
    }
}
