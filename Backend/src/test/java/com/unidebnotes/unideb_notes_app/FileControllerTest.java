package com.unidebnotes.unideb_notes_app;

import com.unidebnotes.unideb_notes_app.controller.FileController;
import com.unidebnotes.unideb_notes_app.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @Test
    public void testUploadFileSuccess() throws Exception {
        // Mock file upload service
        MockMultipartFile file = new MockMultipartFile("file", "testfile.txt", "text/plain", "test content".getBytes());
        when(fileService.uploadFile(file)).thenReturn("testfile.txt");

        // Perform the file upload request
        mockMvc.perform(multipart("/api/files/upload")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("File uploaded successfully: testfile.txt"));
    }

    @Test
    public void testUploadFileFailure() throws Exception {
        // Mock an IOException to simulate failure
        MockMultipartFile file = new MockMultipartFile("file", "testfile.txt", "text/plain", "test content".getBytes());
        when(fileService.uploadFile(file)).thenThrow(new IOException("Failed to upload file"));

        // Perform the file upload request and expect failure
        mockMvc.perform(multipart("/api/files/upload")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to upload file"));
    }
}
