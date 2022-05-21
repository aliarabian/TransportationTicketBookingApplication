package com.platform.business.service.booking.exception;

import com.platform.business.exception.ApplicationException;

public abstract class BookingException extends ApplicationException {

    public BookingException(String message) {
        super(message);
    }


}
