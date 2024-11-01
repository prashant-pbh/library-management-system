package com.library.service;

public interface EmailService
{
    void sendEmail(String email, String emailSubject, String emailBody);
}