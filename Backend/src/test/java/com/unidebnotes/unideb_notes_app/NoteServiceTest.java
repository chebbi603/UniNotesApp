package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.model.Note;
import com.unidebnotes.unideb_notes_app.model.Subject;
import com.unidebnotes.unideb_notes_app.model.User;
import com.unidebnotes.unideb_notes_app.repository.NoteRepository;
import com.unidebnotes.unideb_notes_app.repository.SubjectRepository;
import com.unidebnotes.unideb_notes_app.repository.UserRepository;
import com.unidebnotes.unideb_notes_app.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NoteService noteService;

    private Subject mockSubject;
    private User mockUser;
    private Note mockNote;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock objects
        mockSubject = new Subject(1L, "Mathematics", "Science");
        mockUser = new User(1L, "John Doe", "john.doe@example.com");

        mockNote = new Note();
        mockNote.setId(1L);
        mockNote.setTitle("Note Title");
        mockNote.setMessage("Note Message");
        mockNote.setFilePath("path/to/file");
        mockNote.setSubject(mockSubject);
        mockNote.setAuthor(mockUser);
        mockNote.setPublic(true);
    }

    @Test
    public void testCreateNote() {
        // Arrange
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(mockSubject));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(noteRepository.save(any(Note.class))).thenReturn(mockNote);

        // Act
        Note createdNote = noteService.createNote("Note Title", "Note Message", "path/to/file", 1L, 1L, true);

        // Assert
        assertNotNull(createdNote);
        assertEquals("Note Title", createdNote.getTitle());
        assertEquals("Note Message", createdNote.getMessage());
        assertEquals("path/to/file", createdNote.getFilePath());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    public void testCreateNote_SubjectNotFound() {
        // Arrange
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            noteService.createNote("Note Title", "Note Message", "path/to/file", 1L, 1L, true);
        });
        assertEquals("Subject or User not found", exception.getMessage());
    }

    @Test
    public void testCreateNote_UserNotFound() {
        // Arrange
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(mockSubject));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            noteService.createNote("Note Title", "Note Message", "path/to/file", 1L, 1L, true);
        });
        assertEquals("Subject or User not found", exception.getMessage());
    }

    @Test
    public void testGetNotesBySubjectId() {
        // Arrange
        when(noteRepository.findAllBySubjectId(1L)).thenReturn(List.of(mockNote));

        // Act
        List<Note> notes = noteService.getNotesBySubjectId(1L);

        // Assert
        assertNotNull(notes);
        assertEquals(1, notes.size());
        assertEquals("Note Title", notes.get(0).getTitle());
        verify(noteRepository, times(1)).findAllBySubjectId(1L);
    }

    @Test
    public void testGetNotesByUserId() {
        // Arrange
        when(noteRepository.findAllByAuthorId(1L)).thenReturn(List.of(mockNote));

        // Act
        List<Note> notes = noteService.getNotesByUserId(1L);

        // Assert
        assertNotNull(notes);
        assertEquals(1, notes.size());
        assertEquals("Note Title", notes.get(0).getTitle());
        verify(noteRepository, times(1)).findAllByAuthorId(1L);
    }

    @Test
    public void testDeleteNoteById() {
        // Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.of(mockNote));
        when(noteRepository.existsByFilePath("path/to/file")).thenReturn(false);  // Simulate file not being used by other notes

        // Act
        noteService.deleteNoteById(1L);

        // Assert
        verify(noteRepository, times(1)).delete(mockNote);
        File file = new File("path/to/file");
        assertTrue(file.exists()); // Check if the file was deleted, you may want to mock the File class or its behavior
    }

    @Test
    public void testDeleteNoteById_FileStillReferenced() {
        // Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.of(mockNote));
        when(noteRepository.existsByFilePath("path/to/file")).thenReturn(true);  // Simulate file still being referenced by other notes

        // Act
        noteService.deleteNoteById(1L);

        // Assert
        verify(noteRepository, times(1)).delete(mockNote);
        File file = new File("path/to/file");
        assertFalse(file.exists()); // Check if the file was not deleted
    }

    @Test
    public void testGetNoteById() {
        // Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.of(mockNote));

        // Act
        Optional<Note> note = noteService.getNoteById(1L);

        // Assert
        assertTrue(note.isPresent());
        assertEquals("Note Title", note.get().getTitle());
        verify(noteRepository, times(1)).findById(1L);
    }
}
