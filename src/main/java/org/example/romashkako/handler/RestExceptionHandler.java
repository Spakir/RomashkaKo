package org.example.romashkako.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.example.romashkako.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        return createResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(EntityNotFoundException exception) {
        return createResponseEntity(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return createResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Exception defaultValidationException = null;
        if(!exception.getBindingResult().getFieldErrors().isEmpty()){
            String errorMessage = exception.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
            defaultValidationException = new Exception(errorMessage);
        }else{
            defaultValidationException = new Exception("Ошибка валидации");
        }

        return createResponseEntity(defaultValidationException,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalAccessError.class)
    public ResponseEntity<ErrorResponse> handleIllegalAccessError(IllegalAccessError error) {
        return createResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
