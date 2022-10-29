package com.example.ninjaone.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected Issue handleMethodArgumentNotValid(final MethodArgumentNotValidException ex) {

        final List<String> errors =
                ex.getBindingResult().getAllErrors().stream()
                        .map(
                                error ->
                                        error instanceof FieldError fieldError
                                                ? fieldError.getField() + ": " + fieldError.getDefaultMessage()
                                                : error.getObjectName() + ": " + error.getDefaultMessage())
                        .toList();
        return new Issue("invalid request",errors);
    }

}
