package org.serratec.h2.grupo2.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {

    // Define o diretório de upload a partir do application.properties
    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path fileStorageLocation;

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the upload directory!", ex);
        }
    }

    public String storeFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        int i = originalFilename.lastIndexOf('.');
        if (i > 0) {
            extension = originalFilename.substring(i);
        }
        
        // Gera um nome de arquivo único para evitar conflitos
        String filename = UUID.randomUUID().toString() + extension;
        Path targetLocation = this.fileStorageLocation.resolve(filename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    public byte[] loadFileAsBytes(String filename) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(filename).normalize();
        return Files.readAllBytes(filePath);
    }
    
    public void deleteFile(String filename) throws IOException {
        Path filePath = this.fileStorageLocation.resolve(filename).normalize();
        Files.deleteIfExists(filePath);
    }
} 