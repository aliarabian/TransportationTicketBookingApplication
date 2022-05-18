package com.platform.controllers.auth;

import com.platform.ApiErrorResponse;
import com.platform.ApiResponseEntity;
import com.platform.business.exception.ApplicationException;
import com.platform.business.auth.AuthenticationRequest;
import com.platform.business.auth.AuthenticationResponse;
import com.platform.business.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseEntity<AuthenticationResponse>> login(AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponseEntity<>(authenticationService.authenticate(request)));
        } catch (ApplicationException exception) {
            return ResponseEntity.ok(new ApiResponseEntity<>(new ApiErrorResponse(exception.getMessage(), exception.errorCode())));

        }
    }

}
