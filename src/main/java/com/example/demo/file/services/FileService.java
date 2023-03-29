package com.example.demo.file.services;

import com.example.demo.auth.entities.User;
import com.example.demo.file.entities.File;
import com.example.demo.file.repositories.FileRepository;
import com.example.demo.file.requests.UpdateFileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final IFileOperations fileOperations;
    public File createAndUploadFile(MultipartFile file, String fileName, User user) throws IOException {
        if(file==null || fileName==null || user==null){
            throw new IllegalArgumentException();
        }
        Calendar cal = Calendar.getInstance();
        UUID uuid=UUID.randomUUID();
        String url=fileOperations.uploadFile(file,uuid.toString());
        File fileRecord=File.builder()
                .createdBy(user)
                .fileName(fileName)
                .url(url)
                .createdAt(new Timestamp(cal.getTimeInMillis()).toString())
                .updatedAt(new Timestamp(cal.getTimeInMillis()).toString())
                .build();
        return fileRepository.save(fileRecord);
    }

    public List<File> getFiles(User user) {
        return fileRepository.getFilesByCreatedBy(user);
    }


    public void deleteFile(Long fileId) {//ONLY soft delete , files will be kept in the local storage
        if(fileId==null){
            throw new IllegalArgumentException();
        }
        fileRepository.deleteById(fileId);
    }

    public File getFile(User user, Long fileId) {
        if(user==null || fileId==null){
            throw new IllegalArgumentException();
        }
        return fileRepository.findById(fileId).orElseThrow(NoSuchElementException::new);
    }
}
