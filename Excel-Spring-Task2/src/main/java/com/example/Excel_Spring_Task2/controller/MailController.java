package com.example.Excel_Spring_Task2.controller;

import com.example.Excel_Spring_Task2.entity.User;
import com.example.Excel_Spring_Task2.service.EmailService;
import com.example.Excel_Spring_Task2.service.ExcelReader;
import com.example.Excel_Spring_Task2.service.ZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MailController
{

    @Autowired
    private ExcelReader excelReader;

    @Autowired
    private ZipService zipService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send/email")
    public String sendEmails(@RequestParam("from") String from,
                             @RequestParam("subject") String subject,
                             @RequestParam("body") String body,
                             @RequestParam(value = "cc", required = false) String cc,
                             @RequestParam("zip") MultipartFile zipFile,
                             @RequestParam("excel") MultipartFile excelFile) {

        if (zipFile.isEmpty() || excelFile.isEmpty()) {
            return "Error: ZIP and Excel files must be provided.";
        }

        try {
            // Create a temporary directory for file handling
            String tempDir = System.getProperty("java.io.tmpdir");

            // Save and extract ZIP file
            String zipFilePath = tempDir + File.separator + zipFile.getOriginalFilename();
            zipFile.transferTo(new File(zipFilePath));
            zipService.extractZip(zipFilePath, tempDir);

            // Save uploaded Excel file
            String excelFilePath = tempDir + File.separator + excelFile.getOriginalFilename();
            excelFile.transferTo(new File(excelFilePath));
            Map<String, User> userMap = excelReader.readExcel(excelFilePath);

            // Send emails
            for (Map.Entry<String, User> entry : userMap.entrySet()) {
                User user = entry.getValue();
                List<File> attachments = new ArrayList<>();

                for (String fileName : user.getFileNames()) {
                    File attachment = new File(tempDir + File.separator + fileName);
                    if (attachment.exists()) {
                        attachments.add(attachment);
                    } else {
                        System.out.println("File not found: " + attachment.getAbsolutePath());
                    }
                }

                String customizedBody = body + "<br/><br/>" + user.getData();
                emailService.sendEmailWithAttachments(user.getEmail(), subject, customizedBody, attachments);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error in processing emails: " + e.getMessage();
        }

        return "Vinay Your email sent suessfully to your friends !";
    }
}
