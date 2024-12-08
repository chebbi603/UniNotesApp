package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.model.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    private Subject subject;

    @BeforeEach
    void setUp() {
        subject = new Subject();
    }

    @Test
    void testSetAndGetId() {
        subject.setId(1L);
        assertEquals(1L, subject.getId());
    }

    @Test
    void testSetAndGetName() {
        subject.setName("Mathematics");
        assertEquals("Mathematics", subject.getName());
    }

    @Test
    void testSetAndGetMajor() {
        subject.setMajor("Science");
        assertEquals("Science", subject.getMajor());
    }

    @Test
    void testBuilderPattern() {
        Subject subject = Subject.builder()
                .id(1L)
                .name("Physics")
                .major("Science")
                .build();

        assertNotNull(subject);
        assertEquals(1L, subject.getId());
        assertEquals("Physics", subject.getName());
        assertEquals("Science", subject.getMajor());
    }

    @Test
    void testNoArgsConstructor() {
        Subject subject = new Subject();
        assertNotNull(subject);
    }

    @Test
    void testAllArgsConstructor() {
        Subject subject = new Subject(1L, "Chemistry", "Science");
        assertNotNull(subject);
        assertEquals(1L, subject.getId());
        assertEquals("Chemistry", subject.getName());
        assertEquals("Science", subject.getMajor());
    }

    @Test
    void testSetAndGetSubjectProperties() {
        subject.setId(2L);
        subject.setName("Computer Science");
        subject.setMajor("Engineering");

        assertEquals(2L, subject.getId());
        assertEquals("Computer Science", subject.getName());
        assertEquals("Engineering", subject.getMajor());
    }
}
