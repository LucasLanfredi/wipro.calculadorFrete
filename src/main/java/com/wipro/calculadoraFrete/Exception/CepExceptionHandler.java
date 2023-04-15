package com.wipro.calculadoraFrete.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CepExceptionHandler {

    @ExceptionHandler(CepNotFoundException.class)
    public ResponseEntity<String> handleCepNotFoundException(CepNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}