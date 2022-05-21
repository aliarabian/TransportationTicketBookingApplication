package com.platform.business.service.auth;

public final class AuthenticationResponse {

    private final String token;
    private final Long id;
    private final String username;

    public AuthenticationResponse(String token, String username, Long id) {
        this.token = token;
        this.username = username;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
