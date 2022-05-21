package com.platform.business.service.auth;

import com.platform.business.service.auth.exception.AuthenticationFailedException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request) throws AuthenticationFailedException;
}
