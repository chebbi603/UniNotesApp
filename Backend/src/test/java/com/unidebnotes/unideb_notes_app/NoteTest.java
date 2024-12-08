package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.model.Note;
import com.unidebnotes.unideb_notes_app.model.User;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {

    @Test
    void testNoteCreation() {
        User author = new User();
        File file = new File("path/to/file.txt");

        Note note = Note.builder()
                .file(file)
                .message("This is a test message")
                .title("Test Title")
                .courseName("Test Course")
                .author(author)
                .major("Computer Science")
                .isPublic(true)
                .build();

        assertNotNull(note);
        assertEquals(file, note.getFile());
        assertEquals("This is a test message", note.getMessage());
        assertEquals("Test Title", note.getTitle());
        assertEquals("Test Course", note.getCourseName());
        assertEquals(author, note.getAuthor());
        assertEquals("Computer Science", note.getMajor());
        assertTrue(note.isPublic());
    }

    @Test
    void testUploadDateIsSetOnCreate() {
        Note note = new Note();
        note.onCreate();

        assertNotNull(note.getUploadDate());
        assertTrue(note.getUploadDate().isBefore(LocalDateTime.now()) || note.getUploadDate().isEqual(LocalDateTime.now()));
    }
}
