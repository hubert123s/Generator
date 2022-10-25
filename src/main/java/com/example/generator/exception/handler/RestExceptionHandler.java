package com.example.generator.exception.handler;

import com.example.generator.exception.WrongMinAndMaxValueException;
import com.example.generator.exception.NotFoundValidCharactersException;
import com.example.generator.exception.FileNotFoundException;
import com.example.generator.exception.TooManyStringsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<String> handleFileNotFoundException(FileNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(TooManyStringsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleTooManyStringsException() {
        return ResponseEntity
                .badRequest()
                .body("Can't create that many strings");
    }

    @ExceptionHandler(NotFoundValidCharactersException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleNotFoundValidCharactersException(NotFoundValidCharactersException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
    @ExceptionHandler(WrongMinAndMaxValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleWrongMinAndMaxValueException(WrongMinAndMaxValueException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
