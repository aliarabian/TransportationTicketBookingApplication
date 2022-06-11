package com.platform.aop;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MDCFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MDC.put("ipAddress", request.getRemoteAddr());
        MDC.put("requestURI", request.getRequestURI());
        MDC.put("username", request.getRemoteUser());
        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
