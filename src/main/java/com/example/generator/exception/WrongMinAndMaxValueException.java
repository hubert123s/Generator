package com.example.generator.exception;

import javax.validation.constraints.Min;

public class WrongMinAndMaxValueException extends RuntimeException {
    public WrongMinAndMaxValueException(@Min(value = 1, message = "Min value must be at least 1") int min, int max) {
        super("Max value:"+max+" must be greather than Min value:"+min);
    }
}
