package com.unidebnotes.unideb_notes_app.controller;

import com.unidebnotes.unideb_notes_app.model.Note;
import com.unidebnotes.unideb_notes_app.service.FilesService;
import com.unidebnotes.unideb_notes_app.service.NoteService;
import com.unidebnotes.unideb_notes_app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:3000")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @Autowired
    private FilesService fileService;

    private final Path fileStorageLocation = Path.of("uploads"); // Define upload directory

    // Create a new note with a file
    @PostMapping("/create")
    public ResponseEntity<Note> createNote(
            @RequestParam("title") String title,
            @RequestParam("message") String message,
            @RequestParam("file") MultipartFile file,
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("userId") Long userId,
            @RequestParam("isPublic") boolean isPublic) throws IOException {

        // Save the file to the server
        String fileName = saveFile(file);
        // Create the Note object and set filePath
        Note note = noteService.createNote(title, message, fileName, subjectId, userId, isPublic);

        return ResponseEntity.ok(note);
    }

    // Helper method to save the uploaded file
    private String saveFile(MultipartFile file) throws IOException {
        // Ensure the upload directory exists
        if (!Files.exists(fileStorageLocation)) {
            Files.createDirectories(fileStorageLocation);
        }

        // Generate a unique file name and save the file
        String fileName = file.getOriginalFilename();
        Path targetLocation = fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return targetLocation.toString(); // Return the path to the file
    }

    // Get all notes by subject ID
    @GetMapping("/subject/{subjectId}")
    public List<Note> getNotesBySubject(@PathVariable Long subjectId) {
        return noteService.getNotesBySubjectId(subjectId);
    }


    @GetMapping("/search/{keyword}")
    public List<Note> getNotesBySearch(@PathVariable String keyword) {
        return noteService.searchNotes(keyword);
    }

    @GetMapping("/getAll/")
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    /*// Delete a note by ID
    @DeleteMapping("/{noteId}")
    public ResponseEntity<String> deleteNote(@PathVariable Long noteId) {
        noteService.deleteNoteById(noteId);
        return ResponseEntity.ok("Note deleted successfully");
    }*/

    @GetMapping("/{userId}/notes")
    public ResponseEntity<List<Note>> getNotesForUser(@PathVariable Long userId) {
        List<Note> notes = noteService.getNotesByUserId(userId);
        return ResponseEntity.ok(notes);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<String> deleteNote(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long noteId) {

        // Ensure token is valid
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized request");
        }

        String token = authorization.substring(7); // Remove "Bearer " prefix
        Long userId;
        try {
            userId = userService.getUserIdFromToken(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        // Get the note and check ownership
        Optional<Note> noteOptional = noteService.getNoteById(noteId);
        if (noteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
        }

        Note note = noteOptional.get();

        // Ensure only the note's author can delete the note
        if (!note.getAuthor().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this note");
        }

        noteService.deleteNoteById(noteId);

        return ResponseEntity.ok("Note deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> viewNote(@PathVariable Long id) {
        Optional<Note> noteOptional = noteService.getNoteById(id);
        if (noteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Note note = noteOptional.get();
        return ResponseEntity.ok(note);
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<?> viewFile(@PathVariable Long id) {
        Optional<Note> noteOptional = noteService.getNoteById(id);
        if (noteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
        }
        Note note = noteOptional.get();
        String filePath = note.getFilePath();
        if (filePath == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            File file = new File(filePath);
            if (Files.exists(file.toPath())) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=" + file.getName())
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(Files.readAllBytes(file.toPath()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Could not read file.");
        }
    }

}
