package com.platform.business.service.booking.exception;

public class SectionNotExistsException extends BookingException {
    public SectionNotExistsException(String message) {
        super(message);
    }

    @Override
    public int errorCode() {
        return 2001;
    }
}
