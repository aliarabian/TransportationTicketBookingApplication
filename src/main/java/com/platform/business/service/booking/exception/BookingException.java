package com.platform.business.service.booking.exception.booking;

import com.platform.business.service.booking.exception.ApplicationException;

public abstract class BookingException extends ApplicationException {

    public BookingException(String message) {
        super(message);
    }


}
