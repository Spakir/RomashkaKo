package org.example.romashkako.exception;

public class ProductDTOAlreadyExistsException extends RuntimeException{

    public ProductDTOAlreadyExistsException(String message) {
        super(message);
    }
}
