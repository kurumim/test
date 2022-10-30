package com.example.ninjaone.exceptions;

import java.util.Collections;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  public static final String INVALID_REQUEST = "invalid request";

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
    return new Issue(INVALID_REQUEST, errors);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected Issue handleDataIntegrityViolationException(final DataIntegrityViolationException ex) {
    return new Issue(
        INVALID_REQUEST, Collections.singletonList(ex.getMostSpecificCause().getMessage()));
  }

  @ExceptionHandler(ValidOperationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected Issue handleDataIntegrityViolationException(final ValidOperationException ex) {
    return new Issue(INVALID_REQUEST, Collections.singletonList(ex.getMessage()));
  }
}
