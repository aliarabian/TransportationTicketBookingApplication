package com.platform;

public class ResponseEntity<T> {
    private final T data;
    private final boolean errorCode;

    public ResponseEntity(T data, boolean errorCode) {
        this.data = data;
        this.errorCode = errorCode;
    }

    public boolean isErrorCode() {
        return errorCode;
    }

    public T getData() {
        return data;
    }
}
