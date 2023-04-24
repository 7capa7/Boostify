package com.capa.boostify.exception;

public class InvalidRegisterDataException extends RuntimeException {
    public InvalidRegisterDataException() {
        super("Invalid Data! Cannot create account");
    }
}
