package com.example.generator.exception;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(Long id) {
        super("Can not found File with id:" + id);
    }
}
