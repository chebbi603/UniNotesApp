package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.model.User;
import com.unidebnotes.unideb_notes_app.repository.UserRepository;
import com.unidebnotes.unideb_notes_app.service.UserService;
import com.unidebnotes.unideb_notes_app.service.VerificationCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VerificationCodeService verificationCodeService;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("testuser@example.com");
        mockUser.setPassword("hashedpassword");
        mockUser.setActive(false);
    }

    @Test
    public void testRegisterUser_EmailAlreadyExists() {
        when(userRepository.existsByEmail(mockUser.getEmail())).thenReturn(true);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(mockUser);
        });

        assertEquals("Email is already in use", exception.getMessage());
        verify(userRepository, times(0)).save(mockUser);
    }

    @Test
    public void testRegisterUser_Success() {
        when(userRepository.existsByEmail(mockUser.getEmail())).thenReturn(false);
        when(userRepository.save(mockUser)).thenReturn(mockUser);
        when(verificationCodeService.generateVerificationCode()).thenReturn("123456");

        userService.registerUser(mockUser);
        verify(userRepository, times(1)).save(mockUser);
        verify(verificationCodeService, times(1)).generateVerificationCode();
        verify(verificationCodeService, times(1)).sendVerificationEmail(anyString(), eq("123456"), anyString());
    }

    @Test
    public void testLoginUser_InvalidEmail() {
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.loginUser(mockUser.getEmail(), "password");
        });

        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    public void testLoginUser_AccountNotActivated() {
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.loginUser(mockUser.getEmail(), "password");
        });

        assertEquals("Account is not activated. Please verify your email.", exception.getMessage());
    }

    @Test
    public void testLoginUser_Success() {

        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(verificationCodeService.matches(anyString(), eq(mockUser.getPassword()))).thenReturn(true);

        String token = userService.loginUser(mockUser.getEmail(), "password");
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void testLogoutUser_Success() {
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        String sessionToken = UUID.randomUUID().toString();
        userService.loginUser(mockUser.getEmail(), "password");
        userService.logoutUser(mockUser.getEmail());

        assertThrows(IllegalArgumentException.class, () -> {
            userService.logoutUser(mockUser.getEmail());
        });
    }

    @Test
    public void testActivateUserViaLink_InvalidEmail() {

        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.activateUserViaLink(mockUser.getEmail(), "code");
        });

        assertEquals("Invalid email address", exception.getMessage());
    }

    @Test
    public void testActivateUserViaLink_Success() {

        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(verificationCodeService.validateCode(anyString(), eq("123456"))).thenReturn(true);

        userService.activateUserViaLink(mockUser.getEmail(), "123456");
        assertTrue(mockUser.isActive());
        verify(userRepository, times(1)).save(mockUser);
    }

    @Test
    public void testRequestPasswordReset_EmailNotFound() {

        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.requestPasswordReset(mockUser.getEmail());
        });

        assertEquals("Email not found", exception.getMessage());
    }

    @Test
    public void testValidateAndResetPassword_Success() {

        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(verificationCodeService.validateCode(anyString(), eq("123456"))).thenReturn(true);
        userService.validateAndResetPassword(mockUser.getEmail(), "123456", "newPassword");

        verify(userRepository, times(1)).save(mockUser);
        assertEquals("newPassword", mockUser.getPassword());
    }
}
