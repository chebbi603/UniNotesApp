package com.unidebnotes.unideb_notes_app.controller;

import com.unidebnotes.unideb_notes_app.model.User;
import com.unidebnotes.unideb_notes_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully! Please verify your email.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // New: Email Verification Endpoint
    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@Valid @RequestParam String email, @RequestParam String code) {
        try {
            userService.verifyEmail(email, code);
            return ResponseEntity.ok("Email verified successfully. You can now log in.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestParam String email, @RequestParam String password) {
        try {
            String token = userService.loginUser(email, password);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // Logout Endpoint
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@Valid @RequestParam String email) {
        try {
            userService.logoutUser(email);
            return ResponseEntity.ok("User logged out successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
