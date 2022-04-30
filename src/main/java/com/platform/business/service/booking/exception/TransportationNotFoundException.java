package com.platform.business.service.booking.exception;

public class TransportationNotFoundException extends ResourceNotFoundException{
    public TransportationNotFoundException() {
        super();
    }

    public TransportationNotFoundException(String message) {
        super(message);
    }

    @Override
    protected int errorCode() {
        return 1002;
    }
}
