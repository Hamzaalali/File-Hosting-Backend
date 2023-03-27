package com.example.demo.auth.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "Email can't be empty")
    @Email
    String email;
    @NotEmpty (message = "Password can't be empty")
    String password;
}
