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
        // Initialize a sample file object to test with
        file = Files.builder()
                .name("sample.txt")
                .type("text/plain")
                .path("/uploads/sample.txt")
                .imageData(new byte[]{1, 2, 3})
                .build();

        // Save the file to the repository before each test
        fileRepository.save(file);
    }

    @Test
    void testFindByName() {
        // Test the findByName method
        Files foundFile = fileRepository.findByName("sample.txt");

        assertNotNull(foundFile);
        assertEquals("sample.txt", foundFile.getName());
        assertEquals("text/plain", foundFile.getType());
        assertEquals("/uploads/sample.txt", foundFile.getPath());
    }

    @Test
    void testSaveFile() {
        // Test saving a new file
        Files newFile = Files.builder()
                .name("anotherfile.png")
                .type("image/png")
                .path("/uploads/anotherfile.png")
                .imageData(new byte[]{4, 5, 6})
                .build();

        Files savedFile = fileRepository.save(newFile);

        assertNotNull(savedFile.getId()); // Check if the ID was generated
        assertEquals("anotherfile.png", savedFile.getName());
        assertEquals("image/png", savedFile.getType());
        assertEquals("/uploads/anotherfile.png", savedFile.getPath());
    }

    @Test
    void testFindByNameNotFound() {
        // Test finding a non-existing file by name
        Files foundFile = fileRepository.findByName("nonexistent.txt");

        assertNull(foundFile); // Should return null if the file does not exist
    }

    @Test
    void testDeleteFile() {
        // Test deleting a file
        fileRepository.delete(file);

        Files foundFile = fileRepository.findByName("sample.txt");
        assertNull(foundFile); // The file should be deleted
    }
}
