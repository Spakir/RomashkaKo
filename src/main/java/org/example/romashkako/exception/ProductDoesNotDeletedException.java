package org.example.romashkako.exception;

public class ProductDoesNotDeletedException extends RuntimeException{
    public ProductDoesNotDeletedException(String message) {
        super(message);
    }

    public ProductDoesNotDeletedException() {
    }
}
