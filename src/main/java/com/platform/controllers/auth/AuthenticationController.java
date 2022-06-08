package com.platform.controllers.auth;

import com.platform.ApiResponseEntity;
import com.platform.business.service.auth.AuthenticationRequest;
import com.platform.business.service.auth.AuthenticationResponse;
import com.platform.business.service.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Duration;

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
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        ResponseCookie cookie = ResponseCookie.from("auth_token", authenticationResponse.getToken())
                                              .httpOnly(true)
                                              .maxAge(Duration.ofMinutes(10))
//                                                  .secure(true)
                                              .path("/")
                                              .sameSite("strict")
                                              .build();
        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .body(new ApiResponseEntity<>(authenticationResponse));

    }
}
