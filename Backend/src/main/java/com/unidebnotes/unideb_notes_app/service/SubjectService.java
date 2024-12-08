package com.unidebnotes.unideb_notes_app.service;

import com.unidebnotes.unideb_notes_app.model.Subject;
import com.unidebnotes.unideb_notes_app.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findSubjectById(id);
    }

    public List<Subject> getSubjectsByMajorId(String majorId) {
        return subjectRepository.findByMajor(majorId);
    }

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }
}
