package com.unidebnotes.unideb_notes_app.repository;

import com.unidebnotes.unideb_notes_app.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    // You can add custom query methods if needed
    List<Note> findAllBySubjectId(Long subjectId);
    boolean existsByFilePath(String filePath);

    // Custom query to find notes by userId
    List<Note> findAllByAuthorId(Long authorId);

}
