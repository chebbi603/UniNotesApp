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

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(mockSubject));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(noteRepository.save(any(Note.class))).thenReturn(mockNote);

        Note createdNote = noteService.createNote("Note Title", "Note Message", "path/to/file", 1L, 1L, true);

        assertNotNull(createdNote);
        assertEquals("Note Title", createdNote.getTitle());
        assertEquals("Note Message", createdNote.getMessage());
        assertEquals("path/to/file", createdNote.getFilePath());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    public void testCreateNote_SubjectNotFound() {

        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            noteService.createNote("Note Title", "Note Message", "path/to/file", 1L, 1L, true);
        });
        assertEquals("Subject or User not found", exception.getMessage());
    }

    @Test
    public void testCreateNote_UserNotFound() {

        when(subjectRepository.findById(1L)).thenReturn(Optional.of(mockSubject));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            noteService.createNote("Note Title", "Note Message", "path/to/file", 1L, 1L, true);
        });
        assertEquals("Subject or User not found", exception.getMessage());
    }

    @Test
    public void testGetNotesBySubjectId() {

        when(noteRepository.findAllBySubjectId(1L)).thenReturn(List.of(mockNote));

        List<Note> notes = noteService.getNotesBySubjectId(1L);

        assertNotNull(notes);
        assertEquals(1, notes.size());
        assertEquals("Note Title", notes.get(0).getTitle());
        verify(noteRepository, times(1)).findAllBySubjectId(1L);
    }

    @Test
    public void testGetNotesByUserId() {

        when(noteRepository.findByAuthorId(1L)).thenReturn(List.of(mockNote));

        List<Note> notes = noteService.getNotesByUserId(1L);

        assertNotNull(notes);
        assertEquals(1, notes.size());
        assertEquals("Note Title", notes.get(0).getTitle());
        verify(noteRepository, times(1)).findByAuthorId(1L);
    }

    @Test
    public void testDeleteNoteById() {

        when(noteRepository.findById(1L)).thenReturn(Optional.of(mockNote));
        when(noteRepository.existsByFilePath("path/to/file")).thenReturn(false);

        noteService.deleteNoteById(1L);

        verify(noteRepository, times(1)).delete(mockNote);
        File file = new File("path/to/file");
        assertTrue(file.exists());
    }

    @Test
    public void testDeleteNoteById_FileStillReferenced() {
        // Arrange
        when(noteRepository.findById(1L)).thenReturn(Optional.of(mockNote));
        when(noteRepository.existsByFilePath("path/to/file")).thenReturn(true);

        noteService.deleteNoteById(1L);

        verify(noteRepository, times(1)).delete(mockNote);
        File file = new File("path/to/file");
        assertFalse(file.exists());
    }

    @Test
    public void testGetNoteById() {

        when(noteRepository.findById(1L)).thenReturn(Optional.of(mockNote));

        Optional<Note> note = noteService.getNoteById(1L);

        assertTrue(note.isPresent());
        assertEquals("Note Title", note.get().getTitle());
        verify(noteRepository, times(1)).findById(1L);
    }
}
