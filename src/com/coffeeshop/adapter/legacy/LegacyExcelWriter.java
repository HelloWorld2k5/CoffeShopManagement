package com.coffeeshop.adapter.legacy;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.List;

public class LegacyExcelWriter {
    public void write(List<List<Object>> rows, String outputPath) throws Exception {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(outputPath)) {

            // tạo sheet, ghi rows...
            workbook.write(fos);
        }
    }
}