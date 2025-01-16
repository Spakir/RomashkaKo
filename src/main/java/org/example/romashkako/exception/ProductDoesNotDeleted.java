package org.example.romashkako.exception;

public class ProductDoesNotDeleted extends RuntimeException{
    public ProductDoesNotDeleted(String message) {
        super(message);
    }

    public ProductDoesNotDeleted() {
    }
}
