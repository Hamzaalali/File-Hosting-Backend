package com.example.demo.auth.exceptions.Authentication;

import com.example.demo.general.exception.CustomException;

public class FailedToLogout extends CustomException {
    public FailedToLogout() {
        super(400, 3, "an error occured during logout");
    }
}
