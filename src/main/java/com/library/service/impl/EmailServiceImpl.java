package com.library.service.impl;

import com.library.service.EmailService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService
{
    @Override
    public void sendEmail(String email, String emailSubject, String emailBody) {
        // Email server configuration
        String smtpHost = "smtp.gmail.com";          // e.g., "smtp.gmail.com" for Gmail
        String smtpPort = "587";                     // 587 for TLS, 465 for SSL
        final String senderEmail = "official.shuklaprashant@gmail.com";
        final String senderPassword = "your_password";

        // Set up server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");  // Enable STARTTLS for secure connection

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MIME style email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(emailSubject);
            message.setText(emailBody);

            // Send the email
            Transport.send(message);
            System.out.println("Email sent successfully to " + email);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
