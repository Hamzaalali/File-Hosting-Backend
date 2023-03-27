package com.example.demo.file.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateFileRequest {// used to validate requests
    @NotEmpty(message = "File Name Can't Be Empty")
    @Email
    String fileName;
}
