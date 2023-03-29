package com.example.demo.general.exception;

import com.example.demo.general.exception.CustomException;
import jakarta.validation.ConstraintViolation;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleException(MethodArgumentNotValidException ex) {
        String error=ex.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class).getMessage();
        ErrorDto dto = new ErrorDto(0, error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorDto> handleException(CustomException ex) {
        ErrorDto dto = new ErrorDto(ex.getError_code(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.valueOf(ex.getHttp_status())).body(dto);
    }
    public record ErrorDto(int error_code, String message) {
    }

}