package com.platform.security;

import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtBlackListFilter extends OncePerRequestFilter {
    private final JwtBlackListService blackListService;

    public JwtBlackListFilter(JwtBlackListService blackListService) {
        this.blackListService = blackListService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Cookie auth_token = WebUtils.getCookie(request, "auth_token");
        if (auth_token != null && blackListService.contains(auth_token.getValue()))
            throw new CredentialsExpiredException("Token Expired");

        filterChain.doFilter(request, response);
    }
}
