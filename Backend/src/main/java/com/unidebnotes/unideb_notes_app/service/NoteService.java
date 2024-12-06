/*package com.unidebnotes.unideb_notes_app.service;

import com.unidebnotes.unideb_notes_app.model.Note;
import com.unidebnotes.unideb_notes_app.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final FileService fileService; // Inject FileService for file uploads

    @Autowired
    public NoteService(NoteRepository noteRepository, FileService fileService) {
        this.noteRepository = noteRepository;
        this.fileService = fileService;
    }

    // Method to create a new note with file upload
    public Note createNoteWithFile(Note note, MultipartFile file) throws IOException {
        // Upload the file and get the file path
        String filePath = fileService.uploadFile(file, note.getCourseName(), note.getTitle());
        note.setFilePath(filePath); // Set the file path to the note object

        // Save the note in the database
        return noteRepository.save(note);
    }

    // Method to get a note by its ID
    public Optional<Note> getNoteById(Long noteId) {
        return noteRepository.findById(noteId);
    }

    // Method to get all notes
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    // Method to update an existing note
    public Note updateNote(Long noteId, Note noteDetails) {
        Optional<Note> existingNote = noteRepository.findById(noteId);
        if (existingNote.isPresent()) {
            Note existing = existingNote.get();
            existing.setCourseName(noteDetails.getCourseName());
            existing.setTitle(noteDetails.getTitle());
            existing.setMessage(noteDetails.getMessage());
            existing.setMajor(noteDetails.getMajor());
            existing.setPublic(noteDetails.isPublic());
            // Only update the file path if it's provided
            if (noteDetails.getFilePath() != null) {
                existing.setFilePath(noteDetails.getFilePath());
            }
            return noteRepository.save(existing);
        } else {
            throw new RuntimeException("Note not found");
        }
    }

    // Method to delete a note by ID
    public void deleteNoteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    // Method to search for notes by a keyword (searches in title and message)
    public List<Note> searchNotes(String keyword) {
        return noteRepository.findByTitleContainingOrMessageContaining(keyword, keyword);
    }
}
*/