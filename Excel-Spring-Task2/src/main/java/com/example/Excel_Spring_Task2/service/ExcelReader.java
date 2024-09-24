package com.example.Excel_Spring_Task2.service;

import com.example.Excel_Spring_Task2.entity.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Service
public class ExcelReader {

    public Map<String, User> readExcel(String excelPath) throws Exception {
        Map<String, User> userMap = new HashMap<>();
        FileInputStream fis = new FileInputStream(new File(excelPath));
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row

            String name = getCellValue(row.getCell(1));
            String email = getCellValue(row.getCell(2));
            String data = getCellValue(row.getCell(3));
            String fileNames = getCellValue(row.getCell(4));

            List<String> filesList = Arrays.asList(fileNames.split(","));
            List<String> trimmedFilesList = new ArrayList<>();
            for (String fileName : filesList) {
                trimmedFilesList.add(fileName.trim());
            }

            userMap.put(email, new User(name, email, data, trimmedFilesList));
        }

        workbook.close();
        fis.close();
        return userMap;
    }

    private String getCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) {
            return ""; // or handle this case as needed
        }
        return cell.getCellType() == org.apache.poi.ss.usermodel.CellType.STRING ? cell.getStringCellValue() : "";
    }
}
