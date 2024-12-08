package com.unidebnotes.unideb_notes_app;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.unidebnotes.unideb_notes_app.model.User;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUser() {
        User user = User.builder()
                .name("John Doe")
                .email("john.doe@mailbox.unideb.hu")
                .password("Password@123")
                .major("Computer Science")
                .isActive(true)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty(), "No constraint violations should occur for a valid User object.");
    }

    @Test
    void testNameIsBlank() {
        User user = User.builder()
                .name("")
                .email("john.doe@mailbox.unideb.hu")
                .password("Password@123")
                .major("Computer Science")
                .isActive(true)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Should detect a name violation.");
        assertEquals("Name is required", violations.iterator().next().getMessage());
    }

    @Test
    void testEmailInvalidDomain() {
        User user = User.builder()
                .name("John Doe")
                .email("john.doe@gmail.com")
                .password("Password@123")
                .major("Computer Science")
                .isActive(true)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Should detect an invalid email domain.");
        assertEquals("Email must belong to the university domain (@mailbox.unideb.hu)",
                violations.iterator().next().getMessage());
    }

    @Test
    void testPasswordTooShort() {
        User user = User.builder()
                .name("John Doe")
                .email("john.doe@mailbox.unideb.hu")
                .password("Pass@1")
                .major("Computer Science")
                .isActive(true)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Should detect a password length violation.");
        assertEquals("Password must be at least 8 characters long", violations.iterator().next().getMessage());
    }

    @Test
    void testPasswordMissingSpecialCharacter() {
        User user = User.builder()
                .name("John Doe")
                .email("john.doe@mailbox.unideb.hu")
                .password("Password123")
                .major("Computer Science")
                .isActive(true)
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Should detect a missing special character in the password.");
        assertEquals("Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)",
                violations.iterator().next().getMessage());
    }
}
