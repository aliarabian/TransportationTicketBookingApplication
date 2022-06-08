package com.platform.business.service.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class AuthenticationResponse {

    private final String username;

    @JsonIgnore
    private final transient String token;
    private final Long userId;

    public AuthenticationResponse(String token, String username, Long userId) {
        this.token = token;
        this.username = username;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }
}
