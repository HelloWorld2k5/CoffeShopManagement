package com.coffeeshop.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter DEFAULT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static String nowString() {
        return LocalDateTime.now().format(DEFAULT_FORMAT);
    }

    public static String format(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DEFAULT_FORMAT);
    }

    public static LocalDateTime parse(String dateTime) {
        return LocalDateTime.parse(dateTime, DEFAULT_FORMAT);
    }
}