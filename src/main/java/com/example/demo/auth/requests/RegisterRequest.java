package com.example.demo.auth.requests;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    @NotEmpty(message = "Username can't be empty")
    private String userName;
    @Column(unique=true)
    @NotEmpty(message = "Email can't be empty")
    @Email
    private String email;
    @NotEmpty(message = "Password can't be empty")
    @Size(min = 3, max = 15)
    private String password;
}
