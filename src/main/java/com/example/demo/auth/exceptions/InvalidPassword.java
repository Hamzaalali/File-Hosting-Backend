package com.example.demo.auth.exceptions;

import com.example.demo.general.exception.CustomException;

public class InvalidPassword extends CustomException {

    public InvalidPassword() {
        super(403, 5, "Invalid Password");
    }
}
