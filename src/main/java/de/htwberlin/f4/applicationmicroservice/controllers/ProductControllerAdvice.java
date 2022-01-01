package de.htwberlin.f4.applicationmicroservice.controllers;

import java.util.NoSuchElementException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductControllerAdvice {
    /**
     * händelt ConstraintViolationException
     * @param e ConstraintViolationException
     * @return ResponseEntity mit Validierungsfehlermeldung
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Validation Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * händelt IllegalArgumentException
     * @param e IllegalArgumentException
     * @return ResponseEntity mit fehlermeldung
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>("IllegalArgumentException: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * händelt NoSuchElementException
     * @param e NoSuchElementException
     * @return ResponseEntity mit fehlermeldung
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return new ResponseEntity<>("NoSuchElementException: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * händelt MissingServletRequestParameterException
     * @param e MissingServletRequestParameterException
     * @return ResponseEntity mit fehlermeldung
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new ResponseEntity<>("MissingServletRequestParameterException: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
