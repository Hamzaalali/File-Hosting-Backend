package com.example.demo.file.services;

import com.example.demo.auth.entities.User;
import com.example.demo.auth.exceptions.Forbidden;
import com.example.demo.file.entities.File;
import com.example.demo.file.exceptions.UploadFailedException;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final IFileOperations fileOperations;
    public File createAndUploadFile(MultipartFile file, String fileName, User user) throws UploadFailedException {
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
                .deleted(false)
                .build();
        return fileRepository.save(fileRecord);

    }

    public List<File> getFiles(User user) {
        return fileRepository.getFilesByCreatedByAndDeletedIs(user,false);
    }


    public void deleteFile(User user,Long fileId) throws Forbidden {//ONLY soft delete , files will be kept in the local storage
        if(fileId==null){
            throw new IllegalArgumentException();
        }
        File fileRecord=getFile(user,fileId);
        fileRecord.setDeleted(true);
        fileRepository.save(fileRecord);
    }

    public File getFile(User user, Long fileId) throws Forbidden {
        if(user==null || fileId==null){
            throw new IllegalArgumentException();
        }
        File fileRecord=fileRepository.findById(fileId).orElseThrow(NoSuchElementException::new);
        if(!Objects.equals(fileRecord.getCreatedBy().getId(), user.getId()))
        {
            throw new Forbidden();
        }
        return fileRecord;
    }
}
