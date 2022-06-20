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
        if (request.getRequestURI().equals("/auth/login") || request.getRequestURI().equals("/") || request.getRequestURI().endsWith(".woff2") || request.getRequestURI().endsWith(".woff")
                || request.getRequestURI().endsWith(".js") || request.getRequestURI().endsWith(".ico") || request.getRequestURI().endsWith(".css")) {
            filterChain.doFilter(request, response);
            return;
        }
        Cookie auth_token = WebUtils.getCookie(request, "auth_token");
        if (auth_token != null && blackListService.contains(auth_token.getValue()))
            throw new CredentialsExpiredException("Token Expired");

        filterChain.doFilter(request, response);
    }
}
