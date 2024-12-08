package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.model.Note;
import com.unidebnotes.unideb_notes_app.model.Subject;
import com.unidebnotes.unideb_notes_app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteTest {

    @Mock
    private Subject subject;

    @Mock
    private User author;

    private Note note;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        note = new Note();
    }

    @Test
    void testNoteBuilder() {
        // Test note creation using builder
        Note note = Note.builder()
                .id(1L)
                .subject(subject)
                .filePath("path/to/file")
                .message("This is a test note")
                .title("Test Title")
                .author(author)
                .isPublic(true)
                .build();

        assertNotNull(note);
        assertEquals(1L, note.getId());
        assertEquals("Test Title", note.getTitle());
        assertEquals("This is a test note", note.getMessage());
        assertEquals(true, note.isPublic());
    }

    @Test
    void testNotePrePersist() {
        // Test if uploadDate is set before persistence
        Note note = Note.builder()
                .subject(subject)
                .filePath("path/to/file")
                .message("Test message")
                .title("Test Title")
                .author(author)
                .isPublic(true)
                .build();

        note.onCreate();  // manually invoke the PrePersist logic

        assertNotNull(note.getUploadDate());
        assertTrue(note.getUploadDate().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testNoteConstructor() {
        // Test constructor initialization
        Note note = new Note(1L, subject, "path/to/file", "Test message", "Test Title", author, true, LocalDateTime.now());

        assertNotNull(note);
        assertEquals("Test Title", note.getTitle());
        assertEquals("Test message", note.getMessage());
        assertEquals(true, note.isPublic());
    }

    @Test
    void testGettersAndSetters() {
        // Test getters and setters
        note.setId(1L);
        note.setFilePath("path/to/file");
        note.setMessage("Test message");
        note.setTitle("Test Title");
        note.setAuthor(author);
        note.setUploadDate(LocalDateTime.now());

        assertEquals(1L, note.getId());
        assertEquals("path/to/file", note.getFilePath());
        assertEquals("Test message", note.getMessage());
        assertEquals("Test Title", note.getTitle());
        assertEquals(true, note.isPublic());
    }
}
