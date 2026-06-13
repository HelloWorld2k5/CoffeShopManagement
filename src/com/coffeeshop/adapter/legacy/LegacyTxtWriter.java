package com.coffeeshop.adapter.legacy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class LegacyTxtWriter {
    public void write(String content, String outputPath) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(outputPath, StandardCharsets.UTF_8))) {
            writer.write(content);
        }
    }
}