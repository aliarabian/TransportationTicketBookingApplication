package com.platform.business.service.booking.exception;

public class SeatingSectionNotFoundException extends ResourceNotFoundException {
    @Override
    protected int errorCode() {
        return 1003;
    }
}
