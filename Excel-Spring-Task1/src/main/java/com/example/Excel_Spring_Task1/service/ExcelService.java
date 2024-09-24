package com.example.Excel_Spring_Task1.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    private final String FILE_PATH = "data.xlsx";

    public String getFilePath() {
        return FILE_PATH;
    }

    public void createExcelFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] columns = {"S.No", "Name", "Email", "Technology", "Filename"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        // Write to file
        try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    public void insertData(List<String[]> data) throws IOException {
        Workbook workbook = new XSSFWorkbook(FILE_PATH);
        Sheet sheet = workbook.getSheetAt(0);

        int rowCount = sheet.getPhysicalNumberOfRows();
        for (String[] record : data) {
            Row row = sheet.createRow(rowCount++);
            for (int i = 0; i < record.length; i++) {
                row.createCell(i).setCellValue(record[i]);
            }
        }

        // Write to file
        try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }
}
