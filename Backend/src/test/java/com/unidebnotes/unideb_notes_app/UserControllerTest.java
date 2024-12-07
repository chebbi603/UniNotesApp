package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.controller.UserController;
import com.unidebnotes.unideb_notes_app.model.User;
import com.unidebnotes.unideb_notes_app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void contextLoads() {
        org.junit.jupiter.api.Assertions.assertNotNull(context);
    }

    @Test
    void whenRegisterUser_thenReturnSuccess() throws Exception {
        User user = new User();

        doNothing().when(userService).registerUser(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully! Please verify your email."));
    }

    @Test
    void whenRegisterUser_thenReturnBadRequest() throws Exception {
        User user = new User();

        doThrow(new IllegalArgumentException("Invalid user data")).when(userService).registerUser(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid user data"));
    }

    @Test
    void whenVerifyEmail_thenReturnSuccess() throws Exception {
        String email = "test@example.com";
        String code = "123456";

        doNothing().when(userService).verifyEmail(email, code);

        mockMvc.perform(post("/api/users/verify")
                        .param("email", email)
                        .param("code", code))
                .andExpect(status().isOk())
                .andExpect(content().string("Email verified successfully. You can now log in."));
    }

    @Test
    void whenVerifyEmail_thenReturnBadRequest() throws Exception {
        String email = "test@example.com";
        String code = "wrong_code";

        doThrow(new IllegalArgumentException("Invalid verification code")).when(userService).verifyEmail(email, code);

        mockMvc.perform(post("/api/users/verify")
                        .param("email", email)
                        .param("code", code))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid verification code"));
    }

    @Test
    void whenLogin_thenReturnSuccess() throws Exception {
        String email = "test@example.com";
        String password = "password";
        String token = "token";

        when(userService.loginUser(email, password)).thenReturn(token);

        mockMvc.perform(post("/api/users/login")
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string(token));
    }

    @Test
    void whenLogin_thenReturnUnauthorized() throws Exception {
        String email = "test@example.com";
        String password = "wrong_password";

        doThrow(new IllegalArgumentException("Invalid credentials")).when(userService).loginUser(email, password);

        mockMvc.perform(post("/api/users/login")
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid credentials"));
    }

    @Test
    void whenLogout_thenReturnSuccess() throws Exception {
        String email = "test@example.com";

        doNothing().when(userService).logoutUser(email);

        mockMvc.perform(post("/api/users/logout")
                        .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("User logged out successfully"));
    }

    @Test
    void whenLogout_thenReturnBadRequest() throws Exception {
        String email = "test@example.com";

        doThrow(new IllegalArgumentException("Logout error")).when(userService).logoutUser(email);

        mockMvc.perform(post("/api/users/logout")
                        .param("email", email))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Logout error"));
    }
}