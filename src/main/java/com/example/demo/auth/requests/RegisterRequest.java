package com.example.demo.auth.requests;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    @NotEmpty(message = "Username Can't Be Empty")
    @NotBlank(message = "Username Can't Be Blank")
    @Size(min = 4,message = "User Can't Be Blank")
    private String userName;
    @Column(unique=true)
    @NotEmpty(message = "Email Can't Be Empty")
    @Email
    private String email;
    @NotEmpty(message = "Password Can't Be Empty")
    @Size(min = 8, max = 15,message = "Password Size [8-15] Characters")
    private String password;
}
