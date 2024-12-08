package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.controller.SubjectController;
import com.unidebnotes.unideb_notes_app.model.Subject;
import com.unidebnotes.unideb_notes_app.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class SubjectControllerTest {

    @InjectMocks
    private SubjectController subjectController;

    @Mock
    private SubjectService subjectService;

    private Subject subject;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        subject = new Subject();
        subject.setId(1L);
        subject.setName("Mathematics");
    }

    @Test
    void testGetAllSubjects() {
        // Arrange
        when(subjectService.getAllSubjects()).thenReturn(Collections.singletonList(subject));

        // Act
        ResponseEntity<List<Subject>> response = subjectController.getAllSubjects();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Mathematics", response.getBody().get(0).getName());

        verify(subjectService, times(1)).getAllSubjects();
    }

    @Test
    void testCreateSubject() {
        // Arrange
        when(subjectService.createSubject(any(Subject.class))).thenReturn(subject);

        // Act
        ResponseEntity<String> response = subjectController.createSubject(subject);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Subject added successfully", response.getBody());

        verify(subjectService, times(1)).createSubject(subject);
    }

    @Test
    void testGetSubjectByName_found() {
        // Arrange
        when(subjectService.getSubjectByName("Mathematics")).thenReturn(subject);

        // Act
        ResponseEntity<Subject> response = subjectController.getSubjectByName("Mathematics");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Mathematics", response.getBody().getName());

        verify(subjectService, times(1)).getSubjectByName("Mathematics");
    }

    @Test
    void testGetSubjectByName_notFound() {
        // Arrange
        when(subjectService.getSubjectByName("Physics")).thenReturn(null);

        // Act
        ResponseEntity<Subject> response = subjectController.getSubjectByName("Physics");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(subjectService, times(1)).getSubjectByName("Physics");
    }
}
