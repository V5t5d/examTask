package com.perslab.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Service
public class HmacService {

    private static String token;

    public HmacService(Environment environment) {
        token = environment.getProperty("app.security.token");
    }

    public static String generateHMACSignature(String message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(token.getBytes(), "HmacSHA256");
        hmacSHA256.init(secretKeySpec);
        byte[] signatureBytes = hmacSHA256.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    @PostConstruct
    void getToken () {
        log.debug("token : {}", token);
    }
}