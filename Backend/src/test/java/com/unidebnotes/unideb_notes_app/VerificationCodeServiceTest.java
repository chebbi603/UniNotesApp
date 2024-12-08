package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.service.VerificationCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VerificationCodeServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private VerificationCodeService verificationCodeService;

    private String testEmail = "testuser@example.com";
    private String testCode = "123456";
    private String testActivationLink = "http://localhost:8080/activate?code=123456";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateVerificationCode() {
        // Act
        String code = verificationCodeService.generateVerificationCode();

        // Assert
        assertNotNull(code);
        assertTrue(code.matches("\\d{6}"));  // Ensure it is a 6-digit number
    }

    @Test
    public void testSendVerificationEmail_SingleCode() {
        // Arrange
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("unidebnotesapp@gmail.com");
        message.setTo(testEmail);
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + testCode);

        // Act
        verificationCodeService.sendVerificationEmail(testEmail, testCode);

        // Assert
        verify(mailSender, times(1)).send(message);
    }

    @Test
    public void testSendVerificationEmail_WithActivationLink() {
        // Arrange
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("unidebnotesapp@gmail.com");
        message.setTo(testEmail);
        message.setSubject("Account Activation");
        message.setText("Your verification code is: " + testCode +
                "\nAlternatively, click this link to activate your account: " + testActivationLink);

        // Act
        verificationCodeService.sendVerificationEmail(testEmail, testCode, testActivationLink);

        // Assert
        verify(mailSender, times(1)).send(message);
    }

    @Test
    public void testValidateCode_Success() {
        // Act
        boolean isValid = verificationCodeService.validateCode(testCode, testCode);

        // Assert
        assertTrue(isValid);
    }

    @Test
    public void testValidateCode_Failure() {
        // Act
        boolean isValid = verificationCodeService.validateCode(testCode, "wrongCode");

        // Assert
        assertFalse(isValid);
    }
}
