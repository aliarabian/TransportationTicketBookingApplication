package com.platform;

public class ApiResponseEntity<T> {
    private final T data;
    private final boolean error;
    private final ApiErrorResponse errorResponse;

    public ApiResponseEntity(ApiErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
        this.data = null;
        this.error = true;
    }

    public ApiResponseEntity(T data) {
        this.data = data;
        this.error = false;
        this.errorResponse = null;
    }

    public ApiResponseEntity(T data, boolean errorCode) {
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

    public ApiErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
