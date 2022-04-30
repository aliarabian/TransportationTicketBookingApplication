package com.platform.business.service.booking.exception;

public abstract  class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    protected abstract int errorCode();
}
