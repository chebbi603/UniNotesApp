package com.unidebnotes.unideb_notes_app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private File file; // Path or URL to the uploaded file

    @Column(nullable = false)
    private String message; // A short message or description accompanying the note

    @Column(nullable = false)
    private String title; // The title of the note

    @Column(nullable = false)
    private String courseName; // The name of the course associated with the note

    @ManyToOne(fetch = FetchType.LAZY) // Represents the user who authored the note
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(nullable = false)
    private String major; // The field of study or subject associated with the note

    @Column(nullable = false)
    private boolean isPublic; // Visibility of the note (public/private)

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadDate; // The date when the note was uploaded

    @PrePersist
    protected void onCreate() {
        this.uploadDate = LocalDateTime.now(); // Automatically set the upload date before saving
    }
}
