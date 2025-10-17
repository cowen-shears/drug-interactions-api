package com.example.app.error;

import com.example.domain.exceptions.InteractionNotFoundException;
import com.example.domain.model.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            details.put(error.getField(), error.getDefaultMessage()));

        Error error = new Error("VALIDATION_ERROR", "Invalid request parameters", details);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InteractionNotFoundException.class)
    public ResponseEntity<Error> handleNotFound(InteractionNotFoundException ex) {
        Error error = new Error("NOT_FOUND", ex.getMessage(), null);
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Error> handleOpenFdaError(WebClientResponseException ex) {
        Error error = new Error("UPSTREAM_ERROR", "Error fetching data from openFDA API", null);
        return ResponseEntity.status(502).body(error);
    }
}
