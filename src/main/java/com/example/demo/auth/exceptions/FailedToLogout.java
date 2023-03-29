package com.example.demo.auth.exceptions;

import com.example.demo.general.exception.CustomException;

public class FailedToLogout extends CustomException {
    public FailedToLogout() {
        super(403, 3, "Logout Failed");
    }
}
