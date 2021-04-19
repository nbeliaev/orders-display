package dev.fr13.controllers;

import dev.fr13.dtos.ErrorDto;
import dev.fr13.exceptions.NoSuchResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchResourceException.class)
    public ResponseEntity<ErrorDto> handleNoSuchResource(Exception e) {
        var errors = new ErrorDto();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(e.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
