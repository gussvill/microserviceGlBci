package com.globallogic.microserviceglbci.controller;

import com.globallogic.microserviceglbci.exceptions.CustomValidationException;
import com.globallogic.microserviceglbci.exceptions.ErrorObject;
import com.globallogic.microserviceglbci.exceptions.InputValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<List<ErrorObject>> handleInputValidationException(InputValidationException ex) {

        ErrorObject errorObject = new ErrorObject(LocalDateTime.now(), ex.getCodigo(), ex.getDetail());
        List<ErrorObject> errorList = new ArrayList<>();
        errorList.add(errorObject);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
    }
}
