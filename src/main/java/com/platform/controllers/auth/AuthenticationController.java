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
import org.springframework.web.bind.annotation.*;

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
        ResponseCookie cookie = createAuthTokenCookie(authenticationResponse.getToken(), 10);
        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .body(new ApiResponseEntity<>(authenticationResponse));

    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseEntity<Void>> logout() {
        // TODO Implement Add token to blacklist
        ResponseCookie cookie = createAuthTokenCookie("", 0);
        return ResponseEntity.noContent()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .build();

    }

    private ResponseCookie createAuthTokenCookie(String value, int maxAge) {
        return ResponseCookie.from("auth_token", value)
                             .httpOnly(true)
                             .maxAge(maxAge)
//                                                  .secure(true)
                             .path("/")
                             .sameSite("strict")
                             .build();
    }
}
