package com.platform.business.booking.exception;

public class SeatNotAvailableException extends BookingException {
    public SeatNotAvailableException(String message) {
        super(message);
    }

    @Override
    public int errorCode() {
        return 2002;
    }
}
