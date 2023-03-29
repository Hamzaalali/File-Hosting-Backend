package com.example.demo.auth.exceptions;

import com.example.demo.general.exception.CustomException;

public class EmailNotFound extends CustomException {
    public EmailNotFound() {
        super(204, 2, "an account with this email doesn't exists");
    }
}
