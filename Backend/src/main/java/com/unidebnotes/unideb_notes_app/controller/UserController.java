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
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserData(@Valid @RequestParam String email) {
        try{
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("email : User registered successfully! Please verify your email.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //METHOD FOR ACCOUNT ACTIVATION VIA CODE ONLY
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


    //NEW METHOD FOR ACCOUNT ACTIVATION VIA LINK
    @GetMapping("/activate")
    public ResponseEntity<String> activateUserViaLink(
            @RequestParam String email,
            @RequestParam String code) {
        try {
            userService.activateUserViaLink(email, code);
            return ResponseEntity.ok("Account activated successfully.");
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

    // Request Password Reset
    @PostMapping("/password/reset/request")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        try {
            userService.requestPasswordReset(email);
            return ResponseEntity.ok("Password reset verification code sent to your email.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(
            @RequestParam String email,
            @RequestParam String code,
            @RequestParam String newPassword
    ) {
        try {

            userService.validateAndResetPassword(email, code, newPassword);
            return ResponseEntity.ok("Password has been reset successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
