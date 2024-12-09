package com.unidebnotes.unideb_notes_app.service;

import com.unidebnotes.unideb_notes_app.model.Note;
import com.unidebnotes.unideb_notes_app.model.Subject;
import com.unidebnotes.unideb_notes_app.model.User;
import com.unidebnotes.unideb_notes_app.repository.NoteRepository;
import com.unidebnotes.unideb_notes_app.repository.SubjectRepository;
import com.unidebnotes.unideb_notes_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private SubjectRepository subjectRepository;  // Inject the Subject repository

    @Autowired
    private UserRepository userRepository;  // Inject the User repository

    // Method to save the note with file path
    public Note createNote(String title, String message, String filePath, Long subjectId, Long userId, boolean isPublic) {
        // Fetch Subject and User from the database using the provided IDs
        Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
        Optional<User> userOptional = userRepository.findById(userId);

        // Ensure both Subject and User exist
        if (subjectOptional.isEmpty() || userOptional.isEmpty()) {
            throw new IllegalArgumentException("Subject or User not found");
        }

        // Create a new Note object and set the properties
        Note note = new Note();
        note.setTitle(title);
        note.setMessage(message);
        note.setFilePath(filePath);  // Store the file path in the note
        note.setSubject(subjectOptional.get()); // Set the subject fetched from the database
        note.setAuthor(userOptional.get()); // Set the author fetched from the database
        note.setPublic(isPublic);
        return noteRepository.save(note); // Save the note in the database

    }
    public List<Note> getNotesBySubjectId(Long subjectId) {
        return noteRepository.findAllBySubjectId(subjectId); // Query notes by subjectId
    }

    public void deleteNoteById(Long noteId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));

        String filePath = note.getFilePath();

        // Delete the note from the database
        noteRepository.delete(note);

        // Check if other notes reference the same file
        boolean isFileReferenced = noteRepository.existsByFilePath(filePath);

        // Delete the file only if no other notes reference it
        if (!isFileReferenced) {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }
    public List<Note> getNotesByUserId(Long userId) {
        return noteRepository.findByAuthorId(userId);
    }

    public Optional<Note> getNoteById(Long noteId) {
        return noteRepository.findById(noteId);
    }

    public List<Note> searchNotes(String keyword) {
        return noteRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

}
