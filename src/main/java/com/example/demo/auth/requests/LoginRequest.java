package com.example.demo.auth.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "Email Can't Be Empty")
    @Email
    String email;
    @NotEmpty (message = "Password Can't Be Empty")
    String password;
}
