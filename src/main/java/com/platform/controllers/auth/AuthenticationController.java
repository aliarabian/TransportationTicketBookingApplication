package com.platform.controllers.auth;

import com.platform.ApiErrorResponse;
import com.platform.ApiResponseEntity;
import com.platform.business.exception.ApplicationException;
import com.platform.business.service.auth.AuthenticationRequest;
import com.platform.business.service.auth.AuthenticationResponse;
import com.platform.business.service.auth.AuthenticationService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseEntity<AuthenticationResponse>> login(@RequestBody @Valid AuthenticationRequest request) {
        try {
            AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
            return ResponseEntity.ok(new ApiResponseEntity<>(authenticationResponse));

        } catch (AuthenticationException authenticationException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponseEntity<>(new ApiErrorResponse(authenticationException.getMessage(), 14021)));
        }
    }

}
