package com.example.demo.file.controllers;
import com.example.demo.auth.Utils.AuthUtils;
import com.example.demo.auth.annotations.Authenticate;
import com.example.demo.auth.entities.User;
import com.example.demo.file.entities.File;
import com.example.demo.file.requests.UpdateFileRequest;
import com.example.demo.file.services.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping(path = "/file")
@RequiredArgsConstructor
@Validated
public class FileController {
    private final FileService fileService;

    @Authenticate
    @PostMapping("/")
    ResponseEntity<Object> upload(HttpServletRequest request,@RequestParam("file")MultipartFile file, @RequestParam("fileName") String fileName) throws URISyntaxException, IOException {
        User user= AuthUtils.getUserFromRequest(request);
        File fileRecord=fileService.createAndUploadFile(file,fileName,user);
        if(fileRecord==null){
            throw new IllegalArgumentException();
        }
        URI fileUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(fileRecord.getId()).toUri();
        return ResponseEntity.created(fileUri).build();
    }

    @Authenticate
    @GetMapping("/")
    ResponseEntity<List<File>> listFiles(HttpServletRequest request){
        User user= AuthUtils.getUserFromRequest(request);
        return new ResponseEntity<>(fileService.getFiles(user), HttpStatus.OK);
    }
    @Authenticate
    @PostMapping("/{id}/delete")
    ResponseEntity<Object> delete(@PathVariable("id")Long fileId){
        fileService.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }
    @Authenticate
    @GetMapping("/{id}")
    public ResponseEntity<Resource> get(HttpServletRequest request,@PathVariable("id")Long fileId) throws IOException {
        User user= AuthUtils.getUserFromRequest(request);
        File fileRecord=fileService.getFile(user , fileId);
        java.io.File file=new java.io.File(fileRecord.getUrl());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
