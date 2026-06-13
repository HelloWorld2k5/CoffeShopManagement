package com.coffeeshop.adapter.legacy;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.List;

public class LegacyExcelWriter {
    public void write(List<List<Object>> rows, String outputPath) throws Exception {
        try (Workbook workbook = new XSSFWorkbook();
                FileOutputStream fos = new FileOutputStream(outputPath)) {

            Sheet sheet = workbook.createSheet("HoaDon");

            int rowIndex = 0;
            for (List<Object> rowData : rows) {
                Row row = sheet.createRow(rowIndex++);
                int colIndex = 0;
                for (Object cellData : rowData) {
                    Cell cell = row.createCell(colIndex++);
                    if (cellData instanceof String) {
                        cell.setCellValue((String) cellData);
                    } else if (cellData instanceof Number) {
                        cell.setCellValue(((Number) cellData).doubleValue());
                    } else {
                        cell.setCellValue(cellData != null ? cellData.toString() : "");
                    }
                }
            }

            if (!rows.isEmpty()) {
                int colCount = rows.get(0).size();
                for (int i = 0; i < colCount; i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            workbook.write(fos);
        }
    }
}
