package com.unidebnotes.unideb_notes_app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;  // Directory where files will be stored

    // Method to handle the file upload
    public String uploadFile(MultipartFile file) throws IOException {
        // Create the upload directory if it doesn't exist
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate the path where the file will be saved
        Path path = Paths.get(uploadDir + File.separator + file.getOriginalFilename());

        // Transfer the file to the location
        file.transferTo(path.toFile());

        // Return the file path as a string (could be saved in the database or used for further processing)
        return path.toString();
    }
}
