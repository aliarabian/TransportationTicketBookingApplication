package com.platform.controllers.auth;

import com.platform.ErrorResponse;
import com.platform.ResponseEntity;
import com.platform.business.exception.ApplicationException;
import com.platform.business.auth.AuthenticationRequest;
import com.platform.business.auth.AuthenticationResponse;
import com.platform.business.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
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
