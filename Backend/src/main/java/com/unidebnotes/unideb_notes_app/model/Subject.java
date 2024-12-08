package com.unidebnotes.unideb_notes_app.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Subject name

    @Column(nullable = false)
    private String major; // Major associated with the subject

    public Subject(@Valid String name, @Valid String major) {
        this.name = name;
        this.major = major;
    }
}
