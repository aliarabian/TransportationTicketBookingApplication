package com.platform;

public class ResponseEntity<T> {
    private final T data;
    private final boolean error;

    public ResponseEntity(T data, boolean errorCode) {
        this.data = data;
        this.error = errorCode;
    }

    public boolean isError() {
        return error;
    }

    public T getData() {
        return data;
    }
}
