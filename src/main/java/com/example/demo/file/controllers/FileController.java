package com.example.demo.file.controllers;
import com.example.demo.auth.Utils.AuthUtils;
import com.example.demo.auth.annotations.Authenticate;
import com.example.demo.auth.entities.User;
import com.example.demo.file.entities.File;
import com.example.demo.file.requests.UpdateFileRequest;
import com.example.demo.file.services.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    ResponseEntity<List<File>> listFiles(){
        return new ResponseEntity<>(fileService.getFiles(), HttpStatus.OK);
    }

    @Authenticate
    @PutMapping("/{id}")
    ResponseEntity<File> update(@RequestBody() UpdateFileRequest updateFileRequest,@PathVariable("id")Long fileId){
        File fileRecord=fileService.updateFile(updateFileRequest,fileId);
        if(fileRecord==null){
            throw new IllegalArgumentException();
        }
        return new ResponseEntity<>(fileRecord, HttpStatus.OK);
    }
    @Authenticate
    @DeleteMapping("/{id}")
    ResponseEntity<Object> delete(@PathVariable("id")Long fileId){
        fileService.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }
}
