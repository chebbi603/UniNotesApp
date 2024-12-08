package com.unidebnotes.unideb_notes_app.repository;

import com.unidebnotes.unideb_notes_app.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByName(String name);
}
