package com.unidebnotes.unideb_notes_app.service;

import com.unidebnotes.unideb_notes_app.model.User;
import com.unidebnotes.unideb_notes_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;

    @Autowired
    public UserService(UserRepository userRepository, VerificationCodeService verificationCodeService) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.verificationCodeService = verificationCodeService;
    }

    // Register a new user
    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hash the password
        user.setActive(false); // Set inactive initially
        userRepository.save(user); // Save user

        // Generate and send a verification code
        verificationCodeService.generateCode(user.getEmail());
    }

    // Activate user after successful verification
    public void activateUser(String email, String code) {
        if (!verificationCodeService.validateCode(email, code)) {
            throw new IllegalArgumentException("Invalid verification code");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setActive(true);
        userRepository.save(user); // Update user as active
    }
}
