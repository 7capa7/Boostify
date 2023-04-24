package com.capa.boostify.utils;

import com.capa.boostify.exception.InvalidRegisterDataException;
import com.capa.boostify.exception.UserAlreadyExistsException;
import com.capa.boostify.exception.UserDoesNotExistOrPasswordIsInvalidException;
import com.capa.boostify.exception.BoostingOrderAlreadyExistsException;
import com.capa.boostify.exception.InvalidBoostingOrderDataException;
import com.capa.boostify.exception.InvalidDivisionsException;
import com.capa.boostify.exception.InvalidIdException;
import com.capa.boostify.exception.BoosterApplicationAlreadyRegisteredException;
import com.capa.boostify.exception.InvalidBoosterApplicationDataException;
import com.capa.boostify.exception.InvalidBoosterApplicationDecideDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(InvalidRegisterDataException.class)
    public ResponseEntity<ExceptionResponse> invalidRegitserData(InvalidRegisterDataException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("400", exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> userAlreadyExists(UserAlreadyExistsException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("409", exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserDoesNotExistOrPasswordIsInvalidException.class)
    public ResponseEntity<ExceptionResponse> userDoesNotExistOrPassIsInvalid(UserDoesNotExistOrPasswordIsInvalidException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("403", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidBoosterApplicationDataException.class)
    public ResponseEntity<ExceptionResponse> invalidBoosterApplicationData(InvalidBoosterApplicationDataException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("400", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BoosterApplicationAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionResponse> boosterApplicationAlreadyRegistered(BoosterApplicationAlreadyRegisteredException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("409", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidBoosterApplicationDecideDataException.class)
    public ResponseEntity<ExceptionResponse> invalidBoosterApplicationDecideData(InvalidBoosterApplicationDecideDataException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("400", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBoostingOrderDataException.class)
    public ResponseEntity<ExceptionResponse> invalidBoostingOrderData(InvalidBoostingOrderDataException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("400", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BoostingOrderAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> boostingOrderAlreadyExists(BoostingOrderAlreadyExistsException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("409", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidDivisionsException.class)
    public ResponseEntity<ExceptionResponse> invalidDivisions(InvalidDivisionsException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("409", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ExceptionResponse> invalidId(InvalidIdException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("400", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
