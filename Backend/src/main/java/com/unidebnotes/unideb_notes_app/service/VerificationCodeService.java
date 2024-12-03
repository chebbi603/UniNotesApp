package com.unidebnotes.unideb_notes_app.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VerificationCodeService {

    private final Map<String, String> verificationCodes = new HashMap<>();

    // Generate and store a verification code
    public void generateCode(String email) {
        String code = String.valueOf((int) (Math.random() * 9000) + 1000); // Generate a 4-digit code
        verificationCodes.put(email, code);
        sendEmail(email, code); // Simulate email sending
    }

    // Validate the verification code
    public boolean validateCode(String email, String code) {
        return code.equals(verificationCodes.get(email));
    }

    // Simulate email sending (replace with actual email logic)
    private void sendEmail(String email, String code) {
        System.out.println("Verification code sent to " + email + ": " + code);
    }
}
