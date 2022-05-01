package com.platform.business.service.auth;

public final class AuthToken {

    private final String token;

    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
