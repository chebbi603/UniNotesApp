package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.model.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilesTest {

    private Files file;

    @BeforeEach
    void setUp() {
        // Initialize the Files object before each test
        file = new Files();
    }

    @Test
    void testSetAndGetId() {
        file.setId(1L);
        assertEquals(1L, file.getId());
    }

    @Test
    void testSetAndGetName() {
        file.setName("Test File");
        assertEquals("Test File", file.getName());
    }

    @Test
    void testSetAndGetType() {
        file.setType("image/png");
        assertEquals("image/png", file.getType());
    }

    @Test
    void testSetAndGetPath() {
        file.setPath("/path/to/file");
        assertEquals("/path/to/file", file.getPath());
    }

    @Test
    void testSetAndGetImageData() {
        byte[] imageData = new byte[]{1, 2, 3, 4};
        file.setImageData(imageData);
        assertArrayEquals(imageData, file.getImageData());
    }

    @Test
    void testBuilderPattern() {
        Files file = Files.builder()
                .id(1L)
                .name("Test File")
                .type("image/png")
                .path("/path/to/file")
                .imageData(new byte[]{1, 2, 3, 4})
                .build();

        assertNotNull(file);
        assertEquals(1L, file.getId());
        assertEquals("Test File", file.getName());
        assertEquals("image/png", file.getType());
        assertEquals("/path/to/file", file.getPath());
        assertArrayEquals(new byte[]{1, 2, 3, 4}, file.getImageData());
    }

    @Test
    void testNoArgsConstructor() {
        Files file = new Files();
        assertNotNull(file);
    }

    @Test
    void testAllArgsConstructor() {
        Files file = new Files(1L, "Test File", "image/png", "/path/to/file", new byte[]{1, 2, 3, 4});
        assertNotNull(file);
        assertEquals(1L, file.getId());
        assertEquals("Test File", file.getName());
        assertEquals("image/png", file.getType());
        assertEquals("/path/to/file", file.getPath());
        assertArrayEquals(new byte[]{1, 2, 3, 4}, file.getImageData());
    }
}
