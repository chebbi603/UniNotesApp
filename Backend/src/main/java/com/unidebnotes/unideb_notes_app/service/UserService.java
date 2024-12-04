package com.unidebnotes.unideb_notes_app.service;

import com.unidebnotes.unideb_notes_app.model.User;
import com.unidebnotes.unideb_notes_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final VerificationCodeService verificationCodeService;
    private final BCryptPasswordEncoder passwordEncoder;

    // Temporary storage for verification codes (in a real app, use a database or Redis)
    private final ConcurrentHashMap<String, String> verificationCodes = new ConcurrentHashMap<>();

    // Temporary storage for session tokens (in a real app, use a database or Redis)
    private final ConcurrentHashMap<String, String> sessionTokens = new ConcurrentHashMap<>();

    @Autowired
    public UserService(UserRepository userRepository, VerificationCodeService verificationCodeService) {
        this.userRepository = userRepository;
        this.verificationCodeService = verificationCodeService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // Sign-Up Process
    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        user.setPassword(hashPassword(user.getPassword()));
        user.setActive(false);
        userRepository.save(user);

        // Generate and send verification code
        String code = verificationCodeService.generateVerificationCode();
        verificationCodes.put(user.getEmail(), code);
        verificationCodeService.sendVerificationEmail(user.getEmail(), code);
    }

    // Verify Email
    public void verifyEmail(String email, String enteredCode) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid email address");
        }

        String actualCode = verificationCodes.get(email);
        if (actualCode == null || !verificationCodeService.validateCode(enteredCode, actualCode)) {
            throw new IllegalArgumentException("Invalid verification code");
        }

        // Activate the user account
        User user = userOptional.get();
        user.setActive(true);
        userRepository.save(user);

        // Remove the verification code after successful activation
        verificationCodes.remove(email);
    }

    // Login User
    public String loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        User user = userOptional.get();
        if (!user.isActive()) {
            throw new IllegalArgumentException("Account is not activated. Please verify your email.");
        }

        // Verify the password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Generate a session token
        String token = UUID.randomUUID().toString();
        sessionTokens.put(user.getEmail(), token);

        return token;
    }
    // Logout User
    public void logoutUser(String email) {
        if (!sessionTokens.containsKey(email)) {
            throw new IllegalArgumentException("User is not logged in");
        }

        // Invalidate the session
        sessionTokens.remove(email);
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}