package com.perslab.test.service;

import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class HmacService {

    public static String generateHMACSignature(String message, String secret) throws Exception {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        hmacSHA256.init(secretKeySpec);
        byte[] signatureBytes = hmacSHA256.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(signatureBytes);
    }
}
