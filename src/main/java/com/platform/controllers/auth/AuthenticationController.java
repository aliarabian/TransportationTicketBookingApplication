package com.platform.controllers.auth;

import com.platform.ApiErrorResponse;
import com.platform.ApiResponseEntity;
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

    public ApiResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) {
        try {
            return new ApiResponseEntity<>(authenticationService.authenticate(request), false);
        } catch (ApplicationException exception) {
            return new ApiResponseEntity<>(new ApiErrorResponse(exception.getMessage(), exception.errorCode()) );
        }
    }

}
