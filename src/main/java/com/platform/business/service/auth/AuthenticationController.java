package com.platform.business.service.auth;

import com.platform.ErrorResponse;
import com.platform.ResponseEntity;
import com.platform.business.exception.ApplicationException;

public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) {
        try {
            return new ResponseEntity<>(authenticationService.authenticate(request), false);
        } catch (ApplicationException exception) {
            return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), exception.errorCode()) );
        }
    }

}
