package com.vacationpaycalc.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = {
            ConstraintViolationException.class,
            DateValidationException.class,
            NotEnoughDataException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntimeExceptions(RuntimeException exc) {
        log.warn("Получен статус 400 BAD_REQUEST {}", exc.getMessage());
        return new ErrorResponse(exc.getMessage());
    }
}