package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.controller.FilesController;
import com.unidebnotes.unideb_notes_app.model.Files;
import com.unidebnotes.unideb_notes_app.service.FilesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilesControllerTest {

    @Mock
    private FilesService filesService;

    @InjectMocks
    private FilesController filesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStoreFilesIntoDB_success() throws IOException {

        MockMultipartFile file = new MockMultipartFile("file", "testFile.txt", "text/plain", "Test content".getBytes());
        String expectedResponse = "File uploaded successfully into database";

        when(filesService.storeFile(file)).thenReturn(expectedResponse);

        ResponseEntity<String> response = filesController.storeFilesIntoDB(file);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(filesService, times(1)).storeFile(file);
    }

    @Test
    void testGetImage_success() {

        String fileName = "testImage.png";
        byte[] mockData = "Mock image data".getBytes();

        when(filesService.getFiles(fileName)).thenReturn(mockData);

        ResponseEntity<byte[]> response = filesController.getImage(fileName);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(MediaType.valueOf("image/png"), response.getHeaders().getContentType());
        assertArrayEquals(mockData, response.getBody());
        verify(filesService, times(1)).getFiles(fileName);
    }

    @Test
    void testGetAllFiles_success() {

        Files mockFile = Files.builder().name("testFile.txt").type("text/plain").build();
        List<Files> mockFileList = Collections.singletonList(mockFile);

        when(filesService.getAllFiles()).thenReturn(mockFileList);

        ResponseEntity<List<Files>> response = filesController.getAllFiles();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("testFile.txt", response.getBody().get(0).getName());
        verify(filesService, times(1)).getAllFiles();
    }

    @Test
    void testUploadFileIntoFileSystem_success() throws IOException {

        MockMultipartFile file = new MockMultipartFile("file", "testFile.txt", "text/plain", "Test content".getBytes());
        String expectedResponse = "File uploaded successfully into database";

        when(filesService.storeDataIntoFileSystem(file)).thenReturn(expectedResponse);

        ResponseEntity<String> response = filesController.uploadFileIntoFileSystem(file);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(filesService, times(1)).storeDataIntoFileSystem(file);
    }

    @Test
    void testDownloadImageFromFileSystem_success() {

        String fileName = "testImage.png";
        byte[] mockData = "Mock image data".getBytes();

        when(filesService.getFiles(fileName)).thenReturn(mockData);

        ResponseEntity<byte[]> response = filesController.downloadImageFromFileSystem(fileName);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(MediaType.valueOf("image/png"), response.getHeaders().getContentType());
        assertArrayEquals(mockData, response.getBody());
        verify(filesService, times(1)).getFiles(fileName);
    }
}
