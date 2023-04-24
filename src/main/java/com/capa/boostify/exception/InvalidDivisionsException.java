package com.capa.boostify.exception;

public class InvalidDivisionsException extends RuntimeException {
    public InvalidDivisionsException() {
        super("Your exprected division should be higher than actual!");
    }
}
