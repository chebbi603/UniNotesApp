package com.unidebnotes.unideb_notes_app.repository;

import com.unidebnotes.unideb_notes_app.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    // You can add custom query methods if needed
}
