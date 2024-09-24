package com.example.Excel_Spring_Task1.controller;

import com.example.Excel_Spring_Task1.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/create")
    public String createExcel() {
        try {
            excelService.createExcelFile();
            return "Excel file created successfully!";
        } catch (IOException e) {
            return "Error creating Excel file: " + e.getMessage();
        }
    }

    @PostMapping("/insert")
    public String insertData(@RequestBody List<String[]> data) {
        try {
            excelService.insertData(data);
            return "Data inserted successfully!";
        } catch (IOException e) {
            return "Error inserting data: " + e.getMessage();
        }
    }

    @GetMapping("/download")
    public ResponseEntity<FileSystemResource> downloadExcel() {
        File file = new File(excelService.getFilePath());
        if (!file.exists()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FileSystemResource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
