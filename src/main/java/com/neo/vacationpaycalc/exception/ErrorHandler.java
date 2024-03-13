package com.neo.vacationpaycalc.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException exc) {
        log.warn("Получен статус 400 BAD_REQUEST {}", exc.getMessage());
        return new ErrorResponse(exc.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDateValidationException(final DateValidationException exc) {
        log.warn("Получен статус 400 BAD_REQUEST {}", exc.getMessage());
        return new ErrorResponse(exc.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotEnoughDataException(final NotEnoughDataException exc) {
        log.warn("Получен статус 400 BAD_REQUEST {}", exc.getMessage());
        return new ErrorResponse(exc.getMessage());
    }
}