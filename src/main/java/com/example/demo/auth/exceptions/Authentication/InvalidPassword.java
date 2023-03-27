package com.example.demo.auth.exceptions.Authentication;

import com.example.demo.general.exception.CustomException;

public class InvalidPassword extends CustomException {

    public InvalidPassword() {
        super(400, 5, "invalid password");
    }
}
