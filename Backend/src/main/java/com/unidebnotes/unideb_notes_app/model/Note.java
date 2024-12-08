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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject; // Relationship with Subject

    @Column(nullable = false)
    private String filePath; // Path or URL to the uploaded file

    @Column(nullable = false)
    private String message; // A short message or description accompanying the note

    @Column(nullable = false)
    private String title; // The title of the note

    @ManyToOne(fetch = FetchType.EAGER) // Represents the user who authored the note
    @JoinColumn(name = "user_id", nullable = false)
    private User author;


    @Column(nullable = false)
    private boolean isPublic; // Visibility of the note (public/private)

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadDate; // The date when the note was uploaded

    @PrePersist
    public void onCreate() {
        this.uploadDate = LocalDateTime.now(); // Automatically set the upload date before saving
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }
}