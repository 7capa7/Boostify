package com.capa.boostify.utils;

import com.capa.boostify.authentication.exception.InvalidRegisterDataException;
import com.capa.boostify.authentication.exception.UserAlreadyExistsException;
import com.capa.boostify.authentication.exception.UserDoesNotExistOrPasswordIsInvalidException;
import com.capa.boostify.user.exception.BoosterApplicationAlreadyRegisteredException;
import com.capa.boostify.user.exception.InvalidBoosterApplicationDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(InvalidRegisterDataException.class)
    public ResponseEntity<ExceptionResponse> invalidRegitserData(InvalidRegisterDataException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse("400",exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> userAlreadyExists(UserAlreadyExistsException exception){
        ExceptionResponse exceptionResponse = new ExceptionResponse("409",exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserDoesNotExistOrPasswordIsInvalidException.class)
    public ResponseEntity<ExceptionResponse> userDoesNotExistOrPassIsInvalid(UserDoesNotExistOrPasswordIsInvalidException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse("403",ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidBoosterApplicationDataException.class)
    public ResponseEntity<ExceptionResponse> invalidBoosterApplicationData(InvalidBoosterApplicationDataException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse("400",ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BoosterApplicationAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionResponse> BoosterApplicationAlreadyRegistered(BoosterApplicationAlreadyRegisteredException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse("409",ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }
}
