package com.unidebnotes.unideb_notes_app.repository;

import com.unidebnotes.unideb_notes_app.model.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Files, Long> {

    Files findByName(String name);
}