package com.unidebnotes.unideb_notes_app.controller;

import com.unidebnotes.unideb_notes_app.model.Note;
import com.unidebnotes.unideb_notes_app.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

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
}
