package com.unidebnotes.unideb_notes_app.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VerificationCodeService {

    private final JavaMailSender mailSender;

    public VerificationCodeService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Generate a 6-digit verification code
    public String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    // Send the verification code via email
    public void sendVerificationEmail(String toEmail, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("unidebnotesapp@gmail.com"); // This "From" will appear in the email
        message.setTo(toEmail); // Recipient's email address
        message.setSubject("Unideb Notes Verification Code");
        message.setText("Your verification code is: " + verificationCode);

        mailSender.send(message);
        System.out.println("Verification email sent to " + toEmail);
    }

    // Validate the entered code (basic example)
    public boolean validateCode(String enteredCode, String actualCode) {
        return enteredCode.equals(actualCode);
    }
}
