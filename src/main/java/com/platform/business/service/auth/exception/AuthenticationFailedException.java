package com.platform.business.service.auth.exception;

import com.platform.business.exception.ApplicationException;

public class AuthenticationFailedException extends ApplicationException {

    public AuthenticationFailedException(String message) {
        super(message);
    }

    @Override
    public int errorCode() {
        return 1401;
    }
}
