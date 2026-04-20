package com.sr.JournalApp.handler;

import com.sr.JournalApp.exception.EntryNotFoundException;
import com.sr.JournalApp.exception.UserAlreadyExistsException;
import com.sr.JournalApp.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> conflict(UserAlreadyExistsException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> notFound(UserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<String> EntryNotFound(EntryNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
