package com.example.Excel_Spring_Task2.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithAttachments(String to, String subject, String body, List<File> attachments) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // Allow HTML in email body

            for (File attachment : attachments) {
                if (attachment.exists()) {
                    helper.addAttachment(attachment.getName(), attachment);
                } else {
                    logger.warn("Attachment not found: {}", attachment.getAbsolutePath());
                }
            }

            mailSender.send(message);
            logger.info("Email sent to: {}", to);
        } catch (MessagingException e) {
            logger.error("Failed to send email to {}: {}", to, e.getMessage());
            // Handle as needed
        }
    }
}
