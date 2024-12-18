package com.unidebnotes.unideb_notes_app.service;

import com.unidebnotes.unideb_notes_app.model.User;
import com.unidebnotes.unideb_notes_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
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

       /* // Generate and send verification code
        String code = verificationCodeService.generateVerificationCode();
        verificationCodes.put(user.getEmail(), code);
        verificationCodeService.sendVerificationEmail(user.getEmail(), code);*/

        // Generate verification code and activation link
        String code = verificationCodeService.generateVerificationCode();
        verificationCodes.put(user.getEmail(), code);

        // Assume baseURL is dynamically determined or hardcoded for now
        String baseURL = "http://localhost:8080"; // Replace with IP or cloud URL if necessary
        // Construct the activation link
        String activationLink = baseURL + "/api/users/activate?email=" + user.getEmail() + "&code=" + code;

        verificationCodeService.sendVerificationEmail(user.getEmail(), code, activationLink);

        // Schedule deletion after 24 hours if not activated
        scheduleUserDeletion(user.getEmail());
    }

    // Return User
    public User getUserByEmail (String email) {
        if (!sessionTokens.containsKey(email)) {
            throw new IllegalArgumentException("User is not logged in");
        }
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        return user.get();
    }


    //FOR ACTIVATION VIA CODE
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

    public void activateUserViaLink(String email, String code) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid email address");
        }

        String actualCode = verificationCodes.get(email);
        if (actualCode == null || !verificationCodeService.validateCode(code, actualCode)) {
            throw new IllegalArgumentException("Invalid or expired activation link.");
        }

        // Activate user
        User user = userOptional.get();
        user.setActive(true);
        userRepository.save(user);

        // Remove the verification code
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

    public boolean isTokenValid(String token) {
        return sessionTokens.containsValue(token);
    }

    public Long getUserIdFromToken(String token) {
        for (Map.Entry<String, String> entry : sessionTokens.entrySet()) {
            if (entry.getValue().equals(token)) {
                return userRepository.findByEmail(entry.getKey()).get().getId();
            }
        }
        throw new IllegalArgumentException("Invalid Token");
    }

    // Request Password Reset
    public void requestPasswordReset(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Email not found");
        }

        // Generate and send a verification code
        String code = verificationCodeService.generateVerificationCode();
        verificationCodes.put(email, code);
        verificationCodeService.sendVerificationEmail(email, code);
    }

    public void validateAndResetPassword(String email, String enteredCode, String newPassword) {
        // Validate the reset code
        String actualCode = verificationCodes.get(email);
        if (actualCode == null || !enteredCode.equals(actualCode)) {
            throw new IllegalArgumentException("Invalid or expired verification code.");
        }

        // Proceed to reset the password after successful validation
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Email not found.");
        }

        User user = userOptional.get();
        user.setPassword(hashPassword(newPassword));
        userRepository.save(user);

        // Clear the code to ensure one-time use
        verificationCodes.remove(email);
    }

    private void scheduleUserDeletion(String email) {
        new Thread(() -> {
            try {
                Thread.sleep(24 * 60 * 60 * 1000); // Wait for 24 hours
                Optional<User> userOptional = userRepository.findByEmail(email);
                if (userOptional.isPresent() && !userOptional.get().isActive()) {
                    userRepository.delete(userOptional.get());
                    verificationCodes.remove(email);
                    System.out.println("Inactive user deleted: " + email);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

}