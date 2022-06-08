package com.platform.business.service.booking.exception;

import com.platform.business.exception.ApplicationException;

public class BookingException extends ApplicationException {

    public BookingException(String message) {
        super(message);
    }

    @Override
    public int errorCode() {
        return 1409;
    }


}
