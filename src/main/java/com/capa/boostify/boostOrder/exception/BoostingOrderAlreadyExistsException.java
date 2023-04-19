package com.capa.boostify.boostOrder.exception;

public class BoostingOrderAlreadyExistsException extends RuntimeException {
    public BoostingOrderAlreadyExistsException() {
        super("Order Already Exists! Please wait for booster to finish your order.");
    }
}
