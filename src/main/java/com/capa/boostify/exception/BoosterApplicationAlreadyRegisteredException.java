package com.capa.boostify.exception;

public class BoosterApplicationAlreadyRegisteredException extends RuntimeException{
    public BoosterApplicationAlreadyRegisteredException(){super("We already registered your application! Please wait for decision");};
}
