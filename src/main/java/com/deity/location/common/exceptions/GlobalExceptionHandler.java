package com.deity.location.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejar excepciones personalizadas
//    @ExceptionHandler(CustomNotFoundException.class)
//    public Mono<ResponseEntity<String>> handleNotFoundException(CustomNotFoundException ex) {
//        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
//    }
    @ExceptionHandler(CustomNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleNotFoundException(CustomNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse));
    }

    // Manejar excepciones generales
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<String>> handleGeneralException(Exception ex) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + ex.getMessage()));
    }

    // Manejar ResponseStatusException (como errores de validación)
    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<String>> handleResponseStatusException(ResponseStatusException ex) {
        return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(ex.getReason()));
    }
}
