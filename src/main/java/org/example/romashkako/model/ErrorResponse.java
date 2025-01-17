package org.example.romashkako.model;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timeStamp;

    private String errorMessage;

    public ErrorResponse(LocalDateTime timeStamp, String errorMessage) {
        this.timeStamp = timeStamp;
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
