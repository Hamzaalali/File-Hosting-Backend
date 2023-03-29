package com.example.demo.auth.exceptions;

import com.example.demo.general.exception.CustomException;

public class InvalidRefreshToken extends CustomException {
    public InvalidRefreshToken() {
        super(401, 6, "invalid token");
    }
}
