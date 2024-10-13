package com.perslab.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
public class TokenVerificationService implements HandlerInterceptor {

    @Value("${app.security.token}")
    private String token;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenHeader = request.getHeader("Token");
        log.debug("request header Token : {}", tokenHeader);
        if (tokenHeader == null || !tokenHeader.equals(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            log.error("request Token verification FAILED");
            return false;
        }
        log.info("request Token verification PASSED");
        return true;
    }

    @PostConstruct
    void getToken() {
        log.debug("token : {}", token);
    }
}
