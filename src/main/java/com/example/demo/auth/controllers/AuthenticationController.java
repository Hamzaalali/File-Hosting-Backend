package com.example.demo.auth.controllers;
import com.example.demo.auth.annotations.Authenticate;
import com.example.demo.auth.entities.User;
import com.example.demo.auth.exceptions.EmailAlreadyExists;
import com.example.demo.auth.exceptions.FailedToLogout;
import com.example.demo.auth.exceptions.InvalidRefreshToken;
import com.example.demo.auth.exceptions.Forbidden;
import com.example.demo.auth.requests.LoginRequest;
import com.example.demo.auth.requests.RefreshRequest;
import com.example.demo.auth.requests.RegisterRequest;
import com.example.demo.auth.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest registerRequest) throws EmailAlreadyExists {
        User RegisteredUser=authenticationService.registerUser(registerRequest);
        return new ResponseEntity<>(RegisteredUser, HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody() LoginRequest loginRequest) throws Exception {
        System.out.println(loginRequest);
        Map<String ,String> refreshAndAccess=authenticationService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return new ResponseEntity<>(refreshAndAccess, HttpStatus.ACCEPTED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@Valid @RequestBody RefreshRequest refreshRequest) throws InvalidRefreshToken, Forbidden {
        Map<String ,String> accessTokenObject=authenticationService.refresh(refreshRequest.getRefreshToken());
        System.out.println("refresh");
        return new ResponseEntity<>(accessTokenObject,HttpStatus.ACCEPTED);
    }

    @Authenticate
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) throws FailedToLogout {
        User user= (User) request.getAttribute("user");
        authenticationService.logout(user);
        return new ResponseEntity<>(Collections.emptyList(),HttpStatus.ACCEPTED);
    }
}
