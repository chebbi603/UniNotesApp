package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.model.Subject;
import com.unidebnotes.unideb_notes_app.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    private Subject subject;

    @BeforeEach
    public void setUp() {
        // Set up test data
        subject = Subject.builder()
                .name("Mathematics")
                .major("Science")
                .build();

        subjectRepository.save(subject);
    }

    @Test
    public void testFindByName() {
        // Retrieve the subject by name
        Subject foundSubject = subjectRepository.findByName("Mathematics");

        // Assert that the retrieved subject matches the saved subject
        assertNotNull(foundSubject);
        assertEquals("Mathematics", foundSubject.getName());
        assertEquals("Science", foundSubject.getMajor());
    }

    @Test
    public void testFindByNameNotFound() {
        // Try to find a subject that doesn't exist
        Subject foundSubject = subjectRepository.findByName("History");

        // Assert that no subject is found
        assertNull(foundSubject);
    }

    @Test
    public void testSaveSubject() {
        // Create a new subject
        Subject newSubject = Subject.builder()
                .name("History")
                .major("Arts")
                .build();

        // Save the new subject
        Subject savedSubject = subjectRepository.save(newSubject);

        // Assert that the saved subject has an ID and matches the input data
        assertNotNull(savedSubject.getId());
        assertEquals("History", savedSubject.getName());
        assertEquals("Arts", savedSubject.getMajor());
    }

    @Test
    public void testSubjectNotFound() {
        // Assert that there is no subject with a non-existing name
        Subject subject = subjectRepository.findByName("Non-existing subject");
        assertNull(subject);
    }
}
