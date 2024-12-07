package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.controller.UserController;
import com.unidebnotes.unideb_notes_app.model.User;
import com.unidebnotes.unideb_notes_app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        User user = User.builder()
                .name("John Doe")
                .email("john.doe@mailbox.unideb.hu")
                .password("Password@123")
                .major("Computer Science")
                .build();

        doNothing().when(userService).registerUser(user);

        // Act
        ResponseEntity<String> response = userController.registerUser(user);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully! Please verify your email.", response.getBody());
    }

    @Test
    void testRegisterUser_Failure() {
        // Arrange
        User user = User.builder()
                .name("Invalid User")
                .email("invalid.email")
                .password("weak")
                .major("Unknown")
                .build();

        doThrow(new IllegalArgumentException("Invalid email format")).when(userService).registerUser(user);

        // Act
        ResponseEntity<String> response = userController.registerUser(user);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid email format", response.getBody());
    }

    @Test
    void testVerifyEmail_Success() {
        // Arrange
        String email = "john.doe@mailbox.unideb.hu";
        String code = "123456";

        doNothing().when(userService).verifyEmail(email, code);

        // Act
        ResponseEntity<String> response = userController.verifyEmail(email, code);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Email verified successfully. You can now log in.", response.getBody());
    }

    @Test
    void testVerifyEmail_Failure() {
        // Arrange
        String email = "invalid@mailbox.unideb.hu";
        String code = "123456";

        doThrow(new IllegalArgumentException("Verification failed")).when(userService).verifyEmail(email, code);

        // Act
        ResponseEntity<String> response = userController.verifyEmail(email, code);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Verification failed", response.getBody());
    }

    @Test
    void testLogin_Success() {
        // Arrange
        String email = "john.doe@mailbox.unideb.hu";
        String password = "Password@123";
        String token = "sampleToken";

        when(userService.loginUser(email, password)).thenReturn(token);

        // Act
        ResponseEntity<String> response = userController.login(email, password);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("sampleToken", response.getBody());
    }

    @Test
    void testLogin_Failure() {
        // Arrange
        String email = "invalid@mailbox.unideb.hu";
        String password = "wrongPassword";

        doThrow(new IllegalArgumentException("Invalid credentials")).when(userService).loginUser(email, password);

        // Act
        ResponseEntity<String> response = userController.login(email, password);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void testLogout_Success() {
        // Arrange
        String email = "john.doe@mailbox.unideb.hu";

        doNothing().when(userService).logoutUser(email);

        // Act
        ResponseEntity<String> response = userController.logoutUser(email);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User logged out successfully", response.getBody());
    }

    @Test
    void testLogout_Failure() {
        // Arrange
        String email = "nonexistent@mailbox.unideb.hu";

        doThrow(new IllegalArgumentException("User not found")).when(userService).logoutUser(email);

        // Act
        ResponseEntity<String> response = userController.logoutUser(email);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("User not found", response.getBody());
    }
}
