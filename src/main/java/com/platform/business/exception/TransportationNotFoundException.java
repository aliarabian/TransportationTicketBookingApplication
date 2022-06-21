package com.platform.business.exception;

public class TransportationNotFoundException extends ResourceNotFoundException {

    public TransportationNotFoundException(String message) {
        super(message);
    }

    @Override
    public int errorCode() {
        return 1002;
    }
}
