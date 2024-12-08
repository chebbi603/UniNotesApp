package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.model.Files;
import com.unidebnotes.unideb_notes_app.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
public class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    private Files file;

    @BeforeEach
    void setUp() {
        file = Files.builder()
                .name("sample.txt")
                .type("text/plain")
                .path("/uploads/sample.txt")
                .imageData(new byte[]{1, 2, 3})
                .build();

        fileRepository.save(file);
    }

    @Test
    void testFindByName() {
        Files foundFile = fileRepository.findByName("sample.txt");

        assertNotNull(foundFile);
        assertEquals("sample.txt", foundFile.getName());
        assertEquals("text/plain", foundFile.getType());
        assertEquals("/uploads/sample.txt", foundFile.getPath());
    }

    @Test
    void testSaveFile() {
        Files newFile = Files.builder()
                .name("anotherfile.png")
                .type("image/png")
                .path("/uploads/anotherfile.png")
                .imageData(new byte[]{4, 5, 6})
                .build();

        Files savedFile = fileRepository.save(newFile);

        assertNotNull(savedFile.getId());
        assertEquals("anotherfile.png", savedFile.getName());
        assertEquals("image/png", savedFile.getType());
        assertEquals("/uploads/anotherfile.png", savedFile.getPath());
    }

    @Test
    void testFindByNameNotFound() {
        Files foundFile = fileRepository.findByName("nonexistent.txt");

        assertNull(foundFile);
    }

    @Test
    void testDeleteFile() {
        fileRepository.delete(file);

        Files foundFile = fileRepository.findByName("sample.txt");
        assertNull(foundFile);
    }
}
