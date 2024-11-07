package com.library.service;

import javax.mail.util.ByteArrayDataSource;

public interface EmailService
{
    void sendEmail(String email, String emailSubject, String emailBody);
    void sendEmail(String email, String emailSubject, String emailBody, ByteArrayDataSource byteArrayDataSource);

}