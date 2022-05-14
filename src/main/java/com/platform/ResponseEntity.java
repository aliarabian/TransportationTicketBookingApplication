package com.platform;

public class ResponseEntity<T> {
    private final T data;
    private final boolean error;
    private final ErrorResponse errorResponse;

    public ResponseEntity(ErrorResponse errorResponse){
        this.errorResponse = errorResponse;
        this.data = null;
        this.error = true;
    }
    public ResponseEntity(T data, boolean errorCode) {
        this.data = data;
        this.error = errorCode;
        this.errorResponse = null;
    }

    public boolean isError() {
        return error;
    }

    public T getData() {
        return data;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
