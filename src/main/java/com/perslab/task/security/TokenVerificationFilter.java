package com.perslab.task.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenVerificationFilter extends OncePerRequestFilter {

    @Value("${app.security.token}")
    private String configToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Token");

        if (token == null || !token.equals(configToken)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid or missing Token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
