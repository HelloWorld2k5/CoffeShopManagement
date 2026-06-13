package com.coffeeshop.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvoiceIdGenerator {

    public static String generate() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyyMMddHHmmss");

        return "INV"
                + LocalDateTime.now()
                        .format(formatter);
    }
}