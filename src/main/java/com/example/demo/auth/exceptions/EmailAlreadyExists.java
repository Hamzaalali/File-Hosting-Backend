package com.example.demo.auth.exceptions;

import com.example.demo.general.exception.CustomException;

public class EmailAlreadyExists extends CustomException {
    public EmailAlreadyExists() {
        super(409, 1, "Email Already Exists");
    }
}
