package com.capa.boostify.user.exception;

public class InvalidBoosterApplicationDecideDataException extends RuntimeException {
    public InvalidBoosterApplicationDecideDataException() {
        super("Invalid Data! Cannot decide about booster");
    }
}
