package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.controller.NoteController;
import com.unidebnotes.unideb_notes_app.model.Note;
import com.unidebnotes.unideb_notes_app.service.FilesService;
import com.unidebnotes.unideb_notes_app.service.NoteService;
import com.unidebnotes.unideb_notes_app.service.UserService;
import com.unidebnotes.unideb_notes_app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.server.ResponseStatusException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class NoteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NoteService noteService;

    @Mock
    private UserService userService;

    @Mock
    private FilesService fileService;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
    }

    @Test
    void testCreateNote() throws Exception {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Test Note");
        note.setMessage("This is a test note.");
        note.setFilePath("uploads/test-note.txt");

        when(noteService.createNote(anyString(), anyString(), anyString(), anyLong(), anyLong(), anyBoolean()))
                .thenReturn(note);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/notes/create")
                .param("title", "Test Note")
                .param("message", "This is a test note.")
                .param("file", "test-file")
                .param("subjectId", "1")
                .param("userId", "1")
                .param("isPublic", "true")
                .contentType(MediaType.MULTIPART_FORM_DATA);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Note"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("This is a test note."));
    }

    @Test
    void testGetNotesBySubject() throws Exception {
        List<Note> notes = new ArrayList<>();
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Test Note");
        note.setMessage("This is a test note.");
        notes.add(note);

        when(noteService.getNotesBySubjectId(1L)).thenReturn(notes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/notes/subject/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Test Note"));
    }

    @Test
    void testDeleteNote() throws Exception {
        Long noteId = 1L;
        String token = "valid-token";

        Note note = new Note();
        note.setId(noteId);
        note.setAuthor(new User());
        note.getAuthor().setId(1L);  // Set the owner to match the token user ID.

        when(userService.getUserIdFromToken(token)).thenReturn(1L);
        when(noteService.getNoteById(noteId)).thenReturn(Optional.of(note));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/notes/{noteId}", noteId)
                .header("Authorization", "Bearer " + token);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Note deleted successfully"));

        verify(noteService, times(1)).deleteNoteById(noteId);
    }

    @Test
    void testDeleteNoteUnauthorized() throws Exception {
        Long noteId = 1L;
        String token = "invalid-token";

        when(userService.getUserIdFromToken(token)).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/notes/{noteId}", noteId)
                .header("Authorization", "Bearer " + token);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Unauthorized request"));

        verify(noteService, times(0)).deleteNoteById(noteId);
    }

    @Test
    void testDeleteNoteForbidden() throws Exception {
        Long noteId = 1L;
        String token = "valid-token";

        Note note = new Note();
        note.setId(noteId);
        note.setAuthor(new User());
        note.getAuthor().setId(2L);  // Set the owner to a different user.

        when(userService.getUserIdFromToken(token)).thenReturn(1L);
        when(noteService.getNoteById(noteId)).thenReturn(Optional.of(note));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/notes/{noteId}", noteId)
                .header("Authorization", "Bearer " + token);

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().string("You are not authorized to delete this note"));

        verify(noteService, times(0)).deleteNoteById(noteId);
    }
}
