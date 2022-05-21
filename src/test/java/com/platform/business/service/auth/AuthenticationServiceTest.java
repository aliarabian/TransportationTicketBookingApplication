package com.platform.business.service.auth;

import com.platform.business.service.auth.exception.AuthenticationFailedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Given There is a user")
class AuthenticationServiceTest {

    @Nested
    @DisplayName("When user request login with correct credentials")
    class AuthenticationWithCorrectCredentialsTest {

        private final AuthenticationService authenticationService;

        AuthenticationWithCorrectCredentialsTest(AuthenticationService authenticationService) {
            this.authenticationService = authenticationService;
        }

        @Test
        @DisplayName("should return auth token")
        void authenticate() {
            AuthenticationRequest request = new AuthenticationRequest("ali.arabian@gmail.com", "12345");
            AuthenticationResponse token = authenticationService.authenticate(request);
            Assertions.assertEquals(32, token.getToken().length());
        }
    }

    @Nested
    @DisplayName("When user request login with wrong credentials")
    class AuthenticationWithWrongCredentialsTest {

        private final AuthenticationService authenticationService;

        AuthenticationWithWrongCredentialsTest(AuthenticationService authenticationService) {
            this.authenticationService = authenticationService;
        }

        @Test
        @DisplayName("should throw Authentication Failed Exception")
        void authenticationTest() {
            AuthenticationRequest request = new AuthenticationRequest("ali.arabian@gmal.com", "12345");
            Assertions.assertThrows(AuthenticationFailedException.class, () -> authenticationService.authenticate(request));
        }
    }
}