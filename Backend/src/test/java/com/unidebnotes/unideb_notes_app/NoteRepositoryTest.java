package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.model.Note;
import com.unidebnotes.unideb_notes_app.model.Subject;
import com.unidebnotes.unideb_notes_app.model.User;
import com.unidebnotes.unideb_notes_app.repository.NoteRepository;
import com.unidebnotes.unideb_notes_app.repository.SubjectRepository;
import com.unidebnotes.unideb_notes_app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
public class NoteRepositoryTest {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserRepository userRepository;

    private Note note1;
    private Note note2;
    private Subject subject;
    private User user;

    @BeforeEach
    void setUp() {
        subject = Subject.builder()
                .name("Math")
                .major("Engineering")
                .build();
        subjectRepository.save(subject);

        user = User.builder()
                .name("john_doe")
                .password("password")
                .email("john.doe@example.com")
                .build();
        userRepository.save(user);

        note1 = Note.builder()
                .subject(subject)
                .filePath("/uploads/note1.pdf")
                .message("Math Note 1")
                .title("Math Note 1")
                .author(user)
                .isPublic(true)
                .uploadDate(LocalDateTime.now())
                .build();

        note2 = Note.builder()
                .subject(subject)
                .filePath("/uploads/note2.pdf")
                .message("Math Note 2")
                .title("Math Note 2")
                .author(user)
                .isPublic(false)
                .uploadDate(LocalDateTime.now())
                .build();

        noteRepository.save(note1);
        noteRepository.save(note2);
    }

    @Test
    void testFindAllBySubjectId() {
        List<Note> notes = noteRepository.findAllBySubjectId(subject.getId());

        assertNotNull(notes);
        assertEquals(2, notes.size());
        assertTrue(notes.contains(note1));
        assertTrue(notes.contains(note2));
    }

    @Test
    void testExistsByFilePath() {
        boolean exists = noteRepository.existsByFilePath("/uploads/note1.pdf");

        assertTrue(exists);
    }

    @Test
    void testExistsByFilePathNotFound() {
        boolean exists = noteRepository.existsByFilePath("/uploads/non_existent.pdf");

        assertFalse(exists);
    }

    @Test
    void testFindAllByAuthorId() {
        List<Note> notes = noteRepository.findByAuthorId(user.getId());

        assertNotNull(notes);
        assertEquals(2, notes.size());
        assertTrue(notes.contains(note1));
        assertTrue(notes.contains(note2));
    }

    @Test
    void testFindAllByAuthorIdNotFound() {
        User anotherUser = User.builder()
                .name("jane_doe")
                .password("password")
                .email("jane.doe@example.com")
                .build();
        userRepository.save(anotherUser);

        List<Note> notes = noteRepository.findByAuthorId(anotherUser.getId());

        assertNotNull(notes);
        assertTrue(notes.isEmpty());
    }
}
