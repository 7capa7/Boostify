package com.capa.boostify.exception;

public class UserDoesNotExistOrPasswordIsInvalidException extends RuntimeException {
    public UserDoesNotExistOrPasswordIsInvalidException() {
        super("user does not exist, or password is invalid");
    }
}