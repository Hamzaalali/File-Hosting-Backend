package com.example.demo.file.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface IFileOperations {
    String uploadFile(MultipartFile file,String fileId) throws IOException;
}
