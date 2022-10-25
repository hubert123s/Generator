package com.example.generator.exception;

import javax.validation.constraints.NotBlank;

public class NotFoundValidCharactersException extends RuntimeException {
    public NotFoundValidCharactersException(@NotBlank String chars) {
        super(chars + "-incorrectly entered characters, required format [a,b,c] ");
    }
}
