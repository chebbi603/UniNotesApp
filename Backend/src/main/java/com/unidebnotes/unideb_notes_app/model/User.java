package com.unidebnotes.unideb_notes_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users", indexes = @Index(name = "idx_email", columnList = "email"))//map the user class to a database table

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@mailbox\\.unideb\\.hu$",
            message = "Email must belong to the university domain (@mailbox.unideb.hu)"
    )
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)"
    )
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Long major;

    @Column(nullable = false)
    private boolean isActive = false;

    public User() {
    }

    public User(Long id, String name, String email, String password, Long major, boolean isActive) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.major = major;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getMajor() {
        return major;
    }

    public void setMajor(Long major) {
        this.major = major;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
