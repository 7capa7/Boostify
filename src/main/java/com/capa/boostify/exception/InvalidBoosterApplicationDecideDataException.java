package com.capa.boostify.exception;

public class InvalidBoosterApplicationDecideDataException extends RuntimeException {
    public InvalidBoosterApplicationDecideDataException() {
        super("Invalid Data! Cannot decide about booster");
    }
}
