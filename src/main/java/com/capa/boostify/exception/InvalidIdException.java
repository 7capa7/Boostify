package com.capa.boostify.exception;

public class InvalidIdException extends RuntimeException {
    public InvalidIdException() {
        super("There is no boosting order with given id!");
    }
}
