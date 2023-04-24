package com.capa.boostify.exception;

public class InvalidBoostingOrderDataException extends RuntimeException {
    public InvalidBoostingOrderDataException() {
        super("Invalid Data! Cannot create order");
    }
}
