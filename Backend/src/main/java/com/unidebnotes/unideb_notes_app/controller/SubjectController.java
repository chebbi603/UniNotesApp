package com.unidebnotes.unideb_notes_app.controller;

import com.unidebnotes.unideb_notes_app.model.Subject;
import com.unidebnotes.unideb_notes_app.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "http://localhost:3000")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @GetMapping("/major")
    public ResponseEntity<List<Subject>> getSubjectsByMajor(@RequestParam String major) {
        return ResponseEntity.ok(subjectService.getSubjectsByMajorId(major));
    }


    @PostMapping("/create")
    public ResponseEntity<String> createSubject(@RequestParam String name, @RequestParam String major) {
        Subject subject = new Subject(name,major);
        subjectService.createSubject(subject); // Call the service to save the subject
        return ResponseEntity.status(HttpStatus.CREATED).body("Subject added successfully");
    }


    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectByName(@PathVariable Long id) {
        Subject subject = subjectService.getSubjectById(id);
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(subject);
    }
}
