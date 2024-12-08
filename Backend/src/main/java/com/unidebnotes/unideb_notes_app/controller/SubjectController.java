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
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/getAll")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createSubject(@Valid @RequestBody Subject subject) {
        subjectService.createSubject(subject); // Call the service to save the subject
        return ResponseEntity.status(HttpStatus.CREATED).body("Subject added successfully");
    }


    @GetMapping("/{name}")
    public ResponseEntity<Subject> getSubjectByName(@PathVariable String name) {
        Subject subject = subjectService.getSubjectByName(name);
        if (subject == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(subject);
    }
}
