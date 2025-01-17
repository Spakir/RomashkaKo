package org.example.romashkako.handler;

import org.example.romashkako.exception.ProductAlreadyExistsException;
import org.example.romashkako.exception.ProductDoesNotDeletedException;
import org.example.romashkako.exception.ProductNotFoundException;
import org.example.romashkako.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleProductAlreadyExistsException(ProductAlreadyExistsException exception){
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductDoesNotDeletedException.class)
    public ResponseEntity<ErrorResponse> handleProductDoesNotDeletedException(ProductDoesNotDeletedException exception){
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(),exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
