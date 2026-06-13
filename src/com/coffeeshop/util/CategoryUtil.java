package com.coffeeshop.util;

public final class CategoryUtil {
    private CategoryUtil() {
    }

    public static final String COFFEE_MACHINE = "COFFEE_MACHINE";
    public static final String COFFEE_TRADITIONAL = "COFFEE_TRADITIONAL";
    public static final String BAKERY = "BAKERY";
    public static final String COMBO = "COMBO";

    public static final String FILTER_ALL = "ALL";
    public static final String FILTER_COFFEE = "COFFEE";
    public static final String FILTER_BAKERY = "BAKERY";
    public static final String FILTER_COMBO = "COMBO";

    public static boolean isCoffee(String category) {
        if (category == null)
            return false;
        String upper = category.toUpperCase();
        return upper.contains(COFFEE_MACHINE) || upper.contains(COFFEE_TRADITIONAL);
    }

    public static String stripCoffeePrefix(String category) {
        if (category == null)
            return "";
        return category.replace("COFFEE_", "");
    }
}
