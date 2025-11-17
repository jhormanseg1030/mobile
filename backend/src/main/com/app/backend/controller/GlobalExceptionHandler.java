package com.app.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception e) {
        System.out.println("=== GLOBAL EXCEPTION HANDLER ===");
        System.out.println("Exception: " + e.getClass().getName());
        System.out.println("Message: " + e.getMessage());
        e.printStackTrace();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            "{\"error\":\"" + e.getMessage() + "\",\"type\":\"" + e.getClass().getSimpleName() + "\"}"
        );
    }
}
