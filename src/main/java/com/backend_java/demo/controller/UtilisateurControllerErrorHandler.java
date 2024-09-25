package com.backend_java.demo.controller;



import com.backend_java.demo.service.UtilisateurExisteDejaException;
import com.backend_java.demo.service.UtilisateurNonTrouveException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class UtilisateurControllerErrorHandler {
    @ExceptionHandler(UtilisateurNonTrouveException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleUtilisateurNonTrouveException(UtilisateurNonTrouveException ex) {
        return new Error(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @ExceptionHandler(UtilisateurExisteDejaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleUtilisateurExisteDejaException(UtilisateurExisteDejaException ex) {
        return new Error(ex.getMessage());
    }
}
