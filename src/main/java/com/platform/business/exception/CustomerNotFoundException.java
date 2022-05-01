package com.platform.business.exception;

public class CustomerNotFoundException extends ResourceNotFoundException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

    @Override
    public int errorCode() {
        return 1001;
    }
}
