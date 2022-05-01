package com.platform.business.service.auth;

import com.platform.ErrorResponse;
import com.platform.ResponseEntity;
import com.platform.business.exception.ApplicationException;

public class LoginController {
    private final AuthenticationService authenticationService;

    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public ResponseEntity<?> login(AuthenticationRequest request) {
        try {
            return new ResponseEntity<>(authenticationService.authenticate(request), false);
        } catch (ApplicationException exception) {
            return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), exception.errorCode()), true);
        }
    }

}
