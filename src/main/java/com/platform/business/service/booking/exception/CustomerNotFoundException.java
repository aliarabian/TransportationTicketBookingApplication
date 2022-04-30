package com.platform.business.service.booking.exception;

public class CustomerNotFoundException extends ResourceNotFoundException{
    public CustomerNotFoundException() {
        super();
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

    @Override
    protected int errorCode() {
        return 1001;
    }
}
