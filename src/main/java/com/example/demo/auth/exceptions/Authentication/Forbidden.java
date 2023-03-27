package com.example.demo.auth.exceptions.Authentication;
import com.example.demo.general.exception.CustomException;

public class Forbidden extends CustomException {
    public Forbidden() {
        super(403, 7, "forbidden");
    }
}
