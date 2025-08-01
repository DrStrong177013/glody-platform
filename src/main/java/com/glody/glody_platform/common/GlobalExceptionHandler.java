package com.glody.glody_platform.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNumberFormat(NumberFormatException ex) {
        return Map.of(
                "error", "Invalid GPA format",
                "message", ex.getMessage()
        );
    }

    // (Bạn có thể thêm các @ExceptionHandler khác ở đây)
}