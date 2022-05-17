package com.platform.business.auth;

import java.util.Objects;

public final class AuthenticationRequest {
    private final String username;

    private final String password;

    public AuthenticationRequest(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
