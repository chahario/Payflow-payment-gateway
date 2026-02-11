package com.payflow.paymentgateway.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach((error) -> errors.put(error.getField(), error.getDefaultMessage())
                );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Map<String,String>> handleMissingRequestHeaderException(
            MissingRequestHeaderException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("Error", ex.getMessage() + "Header is required!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> handleDataIntegrityViolationException(){
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Payment already processed!");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Map<String,String>> handleObjectOptimisticLockingFailureException(){
        Map<String, String> response = new HashMap<>();
        response.put("Message", "Payment already processed!");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleGenericException(Exception ex){
        Map<String, String> error = new HashMap<>();
        error.put("ERROR", "Internal Server Error!");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

}
