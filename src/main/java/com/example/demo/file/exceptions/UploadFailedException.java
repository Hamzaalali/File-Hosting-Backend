package com.example.demo.file.exceptions;

import com.example.demo.general.exception.CustomException;

public class UploadFailedException extends CustomException {

    public UploadFailedException() {
        super(400, 8, "Upload Failed!");
    }
}
