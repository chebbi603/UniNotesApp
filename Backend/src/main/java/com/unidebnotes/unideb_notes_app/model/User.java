package com.unidebnotes.unideb_notes_app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", indexes = @Index(name = "idx_email", columnList = "email"))//map the user class to a database table
@NoArgsConstructor//:Generates a no-argument constructor
@AllArgsConstructor//:Generates a constructor with all fields as parameters.
@Data //Automatically generates getters, setters, toString, equals, and hashCode methods
@Builder //: Enables the builder pattern to construct objects (e.g., Person.builder().firstName("John").build()).

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
    private String major;

    @Column(nullable = false)
    private boolean isActive = false;

    public User(long l, String johnDoe, String mail) {
    }
}
