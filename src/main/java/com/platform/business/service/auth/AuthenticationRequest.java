package com.platform.business.service.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class AuthenticationRequest {
    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;


    public AuthenticationRequest(String username, String password) {
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
