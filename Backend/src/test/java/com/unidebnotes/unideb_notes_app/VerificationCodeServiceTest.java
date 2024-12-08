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
        String code = verificationCodeService.generateVerificationCode();
        assertNotNull(code);
        assertTrue(code.matches("\\d{6}"));
    }

    @Test
    public void testSendVerificationEmail_SingleCode() {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("unidebnotesapp@gmail.com");
        message.setTo(testEmail);
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + testCode);

        verificationCodeService.sendVerificationEmail(testEmail, testCode);
        verify(mailSender, times(1)).send(message);
    }

    @Test
    public void testSendVerificationEmail_WithActivationLink() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("unidebnotesapp@gmail.com");
        message.setTo(testEmail);
        message.setSubject("Account Activation");
        message.setText("Your verification code is: " + testCode +
                "\nAlternatively, click this link to activate your account: " + testActivationLink);

        verificationCodeService.sendVerificationEmail(testEmail, testCode, testActivationLink);
        verify(mailSender, times(1)).send(message);
    }

    @Test
    public void testValidateCode_Success() {
        boolean isValid = verificationCodeService.validateCode(testCode, testCode);
        assertTrue(isValid);
    }

    @Test
    public void testValidateCode_Failure() {
        boolean isValid = verificationCodeService.validateCode(testCode, "wrongCode");
        assertFalse(isValid);
    }
}
