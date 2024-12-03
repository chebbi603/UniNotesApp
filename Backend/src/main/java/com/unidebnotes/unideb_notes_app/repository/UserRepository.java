package com.unidebnotes.unideb_notes_app.repository;

import com.unidebnotes.unideb_notes_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email); // Check if email already exists
    Optional<User> findByEmail(String email); // Fetch user by email
}
