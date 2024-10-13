package com.perslab.test.configuration;

import com.perslab.test.service.TokenVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

    @Autowired
    private TokenVerificationService checkService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkService);
    }
}
