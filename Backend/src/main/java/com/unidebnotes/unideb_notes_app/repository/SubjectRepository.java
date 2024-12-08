package com.unidebnotes.unideb_notes_app.repository;

import com.unidebnotes.unideb_notes_app.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findSubjectById(Long id);
    List<Subject> findByMajor(String major);
}
