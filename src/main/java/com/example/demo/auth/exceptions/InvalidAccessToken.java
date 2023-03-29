package com.example.demo.auth.exceptions;

import com.example.demo.general.exception.CustomException;

public class InvalidAccessToken extends CustomException {

    public InvalidAccessToken() {
        super(403, 4, "Invalid Token");
    }
}
