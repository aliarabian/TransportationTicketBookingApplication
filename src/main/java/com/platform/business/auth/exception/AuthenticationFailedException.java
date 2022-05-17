package com.platform.business.auth.exception;

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
