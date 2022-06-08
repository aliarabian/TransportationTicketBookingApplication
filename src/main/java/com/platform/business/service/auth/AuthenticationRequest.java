package com.platform.business.service.auth;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public final class AuthenticationRequest {
    @NotNull
    @NotBlank
    @Email
    private final String username;

    @NotNull
    @NotBlank
    @Length(min = 5, max = 20)
    private final String password;


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
