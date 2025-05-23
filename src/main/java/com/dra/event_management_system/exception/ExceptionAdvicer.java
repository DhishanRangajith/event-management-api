package com.dra.event_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.dra.event_management_system.dto.ErrorResponse;

@RestControllerAdvice
public class ExceptionAdvicer {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedExceptionHandler(AccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse("You do not have permission to access this resource.");
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundExceptionHandler(NotFoundException notFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(notFoundException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> notFoundExceptionHandler(ResponseStatusException responseStatusException) {
        ErrorResponse errorResponse = new ErrorResponse(responseStatusException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }



}
