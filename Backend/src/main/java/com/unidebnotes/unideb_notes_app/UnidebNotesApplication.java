package com.unidebnotes.unideb_notes_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.unidebnotes.unideb_notes_app.service.VerificationCodeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UnidebNotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnidebNotesApplication.class, args);
    }


    ////Used to verify email successful email sent
    /*
    @Bean
    CommandLineRunner testEmailSending(VerificationCodeService verificationCodeService) {
        return args -> {
            // Replace this with your test email address
            String testEmail = "useremailreceivingcode@mailbox.unideb.hu";
            String verificationCode = verificationCodeService.generateVerificationCode();

            // Send the email
            verificationCodeService.sendVerificationEmail(testEmail, verificationCode);

            // Log output
            System.out.println("Test email sent to: " + testEmail);
            System.out.println("Verification Code: " + verificationCode);
        };
    }*/

}
