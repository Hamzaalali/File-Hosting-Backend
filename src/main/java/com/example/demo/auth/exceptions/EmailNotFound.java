package com.example.demo.auth.exceptions;

import com.example.demo.general.exception.CustomException;

public class EmailNotFound extends CustomException {
    public EmailNotFound() {
        super(403, 2, "Email Not Found");
    }
}
