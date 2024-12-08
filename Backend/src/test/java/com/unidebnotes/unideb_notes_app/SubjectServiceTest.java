package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.model.Subject;
import com.unidebnotes.unideb_notes_app.repository.SubjectRepository;
import com.unidebnotes.unideb_notes_app.service.SubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    private Subject mockSubject;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock subject
        mockSubject = new Subject(1L, "Mathematics", "Science");
    }

    @Test
    public void testGetAllSubjects() {
        // Arrange
        when(subjectRepository.findAll()).thenReturn(List.of(mockSubject));

        // Act
        List<Subject> subjects = subjectService.getAllSubjects();

        // Assert
        assertNotNull(subjects);
        assertEquals(1, subjects.size());
        assertEquals("Mathematics", subjects.get(0).getName());
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    public void testGetSubjectByName() {
        // Arrange
        when(subjectRepository.findByName("Mathematics")).thenReturn(mockSubject);

        // Act
        Subject subject = subjectService.getSubjectByName("Mathematics");

        // Assert
        assertNotNull(subject);
        assertEquals("Mathematics", subject.getName());
        verify(subjectRepository, times(1)).findByName("Mathematics");
    }

    @Test
    public void testGetSubjectByName_NotFound() {
        // Arrange
        when(subjectRepository.findByName("Physics")).thenReturn(null);

        // Act
        Subject subject = subjectService.getSubjectByName("Physics");

        // Assert
        assertNull(subject);
        verify(subjectRepository, times(1)).findByName("Physics");
    }

    @Test
    public void testCreateSubject() {
        // Arrange
        when(subjectRepository.save(any(Subject.class))).thenReturn(mockSubject);

        // Act
        Subject createdSubject = subjectService.createSubject(mockSubject);

        // Assert
        assertNotNull(createdSubject);
        assertEquals("Mathematics", createdSubject.getName());
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }
}
