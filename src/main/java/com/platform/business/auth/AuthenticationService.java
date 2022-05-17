package com.platform.business.auth;

import com.platform.business.auth.exception.AuthenticationFailedException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationFailedException;
}
