package com.lameute.referentieltournoi.application.error;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;

import java.util.NoSuchElementException;

import static java.util.stream.Collectors.joining;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiControllerAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    protected ResponseEntity<String> invalidRequestHandler(WebExchangeBindException e) {
        return ResponseEntity.badRequest().body(e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(joining(" ")));
    }

    @ExceptionHandler({ServerWebInputException.class})
    ResponseEntity<String> invalidRequestHandler(ServerWebInputException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({ConversionFailedException.class})
    ResponseEntity<String> invalidRequestHandler(ConversionFailedException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({NoSuchElementException.class})
    ResponseEntity<String> invalidRequestHandler(NoSuchElementException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    ResponseEntity<String> invalidRequestHandler(EntityNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
