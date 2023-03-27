package com.example.demo.auth.exceptions.Authentication;

import com.example.demo.general.exception.CustomException;

public class InvalidAccessToken extends CustomException {

    public InvalidAccessToken() {
        super(401, 4, "invalid token");
    }
}
