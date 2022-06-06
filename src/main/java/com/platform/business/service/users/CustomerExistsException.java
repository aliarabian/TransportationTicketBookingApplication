package com.platform.business.service.users;

import com.platform.business.exception.ApplicationException;

public class CustomerExistsException extends ApplicationException {

    public CustomerExistsException(String message) {
        super(message);
    }

    @Override
    public int errorCode() {
        return 1049;
    }
}
