package com.example.demo.auth.exceptions.Authentication;

import com.example.demo.general.exception.CustomException;

public class EmailAlreadyExists extends CustomException {
    public EmailAlreadyExists() {
        super(409, 1, "email already exists , try using another email or reset your password");
    }
}
