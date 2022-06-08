package com.platform;

import java.time.Instant;

public class ApiErrorResponse {
    private final String message;
    private final int errorCode;
    private final Instant timestamp;
    public ApiErrorResponse(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = Instant.now();
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", errorCode=" + errorCode +
                ", timestamp=" + timestamp +
                '}';
    }
}
