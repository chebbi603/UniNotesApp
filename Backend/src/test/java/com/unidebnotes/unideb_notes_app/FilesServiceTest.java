package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.model.Files;
import com.unidebnotes.unideb_notes_app.repository.FileRepository;
import com.unidebnotes.unideb_notes_app.service.FilesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FilesServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FilesService filesService;

    private MultipartFile file;

    private Files mockFile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Initialize mock file
        file = mock(MultipartFile.class);
        mockFile = Files.builder()
                .name("testFile.txt")
                .type("text/plain")
                .imageData(new byte[]{1, 2, 3})
                .build();
    }

    @Test
    public void testStoreFile() throws IOException {
        // Arrange
        when(file.getOriginalFilename()).thenReturn("testFile.txt");
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(fileRepository.save(any(Files.class))).thenReturn(mockFile);

        // Act
        String result = filesService.storeFile(file);

        // Assert
        assertEquals("File uploaded successfully into database", result);
        verify(fileRepository, times(1)).save(any(Files.class));
    }

    @Test
    public void testStoreFile_Failure() throws IOException {
        // Arrange
        when(file.getOriginalFilename()).thenReturn("testFile.txt");
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(fileRepository.save(any(Files.class))).thenReturn(null);

        // Act
        String result = filesService.storeFile(file);

        // Assert
        assertNull(result);
        verify(fileRepository, times(1)).save(any(Files.class));
    }

    @Test
    public void testGetFiles() {
        // Arrange
        when(fileRepository.findByName("testFile.txt")).thenReturn(mockFile);

        // Act
        byte[] result = filesService.getFiles("testFile.txt");

        // Assert
        assertNotNull(result);
        assertEquals(3, result.length);
        verify(fileRepository, times(1)).findByName("testFile.txt");
    }

    @Test
    public void testGetAllFiles() {
        // Arrange
        when(fileRepository.findAll()).thenReturn(List.of(mockFile));

        // Act
        var files = filesService.getAllFiles();

        // Assert
        assertNotNull(files);
        assertEquals(1, files.size());
        verify(fileRepository, times(1)).findAll();
    }

    @Test
    public void testStoreDataIntoFileSystem() throws IOException {
        // Arrange
        when(file.getOriginalFilename()).thenReturn("testFile.txt");
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getBytes()).thenReturn(new byte[]{1, 2, 3});
        when(fileRepository.save(any(Files.class))).thenReturn(mockFile);

        // Act
        String result = filesService.storeDataIntoFileSystem(file);

        // Assert
        assertEquals("File uploaded successfuly into database", result);
        verify(fileRepository, times(1)).save(any(Files.class));
        // You can also verify that file.transferTo() was called, if you mock the file
    }

    @Test
    public void testDownloadFilesFromFileSystem() throws IOException {
        // Arrange
        String filePath = "C:\\Uploads\\testFile.txt";
        when(fileRepository.findByName("testFile.txt")).thenReturn(mockFile);
        when(fileRepository.findByName("testFile.txt").getPath()).thenReturn(filePath);

        // Act
        byte[] result = filesService.downloadFilesFromFileSystem("testFile.txt");

        // Assert
        assertNotNull(result);
        assertEquals(3, result.length);
    }
}
