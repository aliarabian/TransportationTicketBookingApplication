package com.platform.business.service.booking.exception;

public class PassengerExistsException extends BookingException {
    public PassengerExistsException(String message) {
        super(message);
    }

    @Override
    public int errorCode() {
        return 1409;
    }
}
