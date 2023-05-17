package com.bci.microservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase `GlobalExceptionHandler` es un controlador global de excepciones que utiliza el método `handleInputValidationException` para manejar las excepciones `InputValidationException`.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handle input validation exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<List<ErrorObject>> handleInputValidationException(InputValidationException ex) {

        ErrorObject errorObject = new ErrorObject(LocalDateTime.now(), ex.getCodigo(), ex.getDetail());
        List<ErrorObject> errorList = new ArrayList<>();
        errorList.add(errorObject);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
    }
}