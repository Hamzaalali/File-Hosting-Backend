package com.example.demo.file.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class LocalFileOperations implements IFileOperations {
    private String storageUrl = System.getProperty("user.dir") + "\\storage\\";

    @Override
    public String uploadFile(MultipartFile file, String fileId) throws IOException {
        String localUrl = storageUrl + fileId;
        File localFile = new File(localUrl + "." + file.getOriginalFilename().split("\\.")[1]);
        try (OutputStream os = new FileOutputStream(localFile)) {
            os.write(file.getBytes());
        }
        return localFile.getAbsolutePath();
    }
}
