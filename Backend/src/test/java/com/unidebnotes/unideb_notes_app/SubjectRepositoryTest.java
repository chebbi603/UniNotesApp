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

        subject = Subject.builder()
                .name("Mathematics")
                .major("Science")
                .build();

        subjectRepository.save(subject);
    }

    @Test
    public void testFindByName() {

        Subject foundSubject = subjectRepository.findByName("Mathematics");

        assertNotNull(foundSubject);
        assertEquals("Mathematics", foundSubject.getName());
        assertEquals("Science", foundSubject.getMajor());
    }

    @Test
    public void testFindByNameNotFound() {
        Subject foundSubject = (Subject) subjectRepository.findByName("History");

        assertNull(foundSubject);
    }

    @Test
    public void testSaveSubject() {

        Subject newSubject = Subject.builder()
                .name("History")
                .major("Arts")
                .build();

        Subject savedSubject = subjectRepository.save(newSubject);

        assertNotNull(savedSubject.getId());
        assertEquals("History", savedSubject.getName());
        assertEquals("Arts", savedSubject.getMajor());
    }

    @Test
    public void testSubjectNotFound() {
        Subject subject = subjectRepository.findByMajor("Non-existing subject");
        assertNull(subject);
    }
}
