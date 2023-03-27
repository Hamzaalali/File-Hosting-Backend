package com.example.demo.general.exception;

import lombok.Data;

@Data
public class CustomException extends Exception{
    private int http_status;
    private int error_code;
    private String message;

    public CustomException(int http_status, int error_code, String message) {
        this.http_status = http_status;
        this.error_code = error_code;
        this.message = message;
    }
}
