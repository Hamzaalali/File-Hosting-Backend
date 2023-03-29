package com.example.demo.file.services;

import com.example.demo.file.exceptions.UploadFailedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class LocalFileOperations implements IFileOperations {
    private String storageUrl = System.getProperty("user.dir") + "\\storage\\";

    @Override
    public String uploadFile(MultipartFile file, String fileId) throws UploadFailedException {
        try {
            createDirectoryIfNotFound(storageUrl);
            String localUrl = storageUrl + fileId;
            File localFile = new File(localUrl + "." + file.getOriginalFilename().split("\\.")[1]);
            try (OutputStream os = new FileOutputStream(localFile)) {
                os.write(file.getBytes());
            }catch (Exception e){
                localFile.delete();
                throw new UploadFailedException();
            }
            return localFile.getAbsolutePath();
        }catch (Exception exception){
            throw new UploadFailedException();
        }
    }
    public void createDirectoryIfNotFound(String directory) throws IOException {
        Files.createDirectories(Paths.get(directory));
    }
}
