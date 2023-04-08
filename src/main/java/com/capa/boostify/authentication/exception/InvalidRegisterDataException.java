package com.capa.boostify.authentication.exception;

public class InvalidRegisterDataException extends RuntimeException {
    public InvalidRegisterDataException() {
        super("Invalid Data! Cannot create account");
    }
}
