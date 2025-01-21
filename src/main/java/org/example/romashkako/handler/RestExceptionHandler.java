package org.example.romashkako.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.example.romashkako.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<ErrorResponse> createResponseEntity(Throwable exception, HttpStatus httpStatus) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        return createResponseEntity(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(EntityNotFoundException exception) {
        return createResponseEntity(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return createResponseEntity(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalAccessError.class)
    public ResponseEntity<ErrorResponse> handleIllegalAccessError(IllegalAccessError error) {
        return createResponseEntity(error, HttpStatus.CONFLICT);
    }
}
