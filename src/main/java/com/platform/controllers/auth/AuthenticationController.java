package com.platform.controllers.auth;

import com.platform.ApiResponseEntity;
import com.platform.business.service.auth.AuthenticationRequest;
import com.platform.business.service.auth.AuthenticationResponse;
import com.platform.business.service.auth.AuthenticationService;
import com.platform.security.JwtBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Duration;

@RestController
@RequestMapping(value = "auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtBlackListService blackListService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtBlackListService blackListService) {
        this.authenticationService = authenticationService;
        this.blackListService = blackListService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseEntity<AuthenticationResponse>> login(@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        ResponseCookie cookie = createAuthTokenCookie(authenticationResponse.getToken(), Duration.ofMinutes(30));
        return ResponseEntity.ok()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .body(new ApiResponseEntity<>(authenticationResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseEntity<Void>> logout(HttpServletRequest request) {
        Cookie auth_token = WebUtils.getCookie(request, "auth_token");
        blackListService.add(auth_token.getValue());
        ResponseCookie cookie = createAuthTokenCookie("", Duration.ZERO);
        return ResponseEntity.noContent()
                             .header(HttpHeaders.SET_COOKIE, cookie.toString())
                             .build();
    }

    private ResponseCookie createAuthTokenCookie(String value, Duration maxAge) {
        return ResponseCookie.from("auth_token", value)
                             .httpOnly(true)
                             .maxAge(maxAge)
                             .secure(true)
                             .path("/")
                             .sameSite("strict")
                             .build();
    }
}
