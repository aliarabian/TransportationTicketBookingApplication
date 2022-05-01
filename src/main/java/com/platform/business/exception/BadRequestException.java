package com.platform.business.exception;

public class BadRequestException extends ApplicationException {


    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public int errorCode() {
        return 1400;
    }
}
