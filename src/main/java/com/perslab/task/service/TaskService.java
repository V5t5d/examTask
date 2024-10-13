package com.perslab.task.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskService {

    public Map<String, Object> getHeaders(String signature) {
        List<Map<String, String>> headers = List.of(Map.of("signature", signature));
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("result", headers);
        return responseData;
    }

    public String getSignature(String sortedParams) {
        String signature = null;
        try {
            signature = HmacService.generateHMACSignature(sortedParams);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException in service.generateHMACSignature");
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            log.error("InvalidKeyException service.generateHMACSignature");
            throw new RuntimeException(e);
        }
        return signature;
    }

    public String getParamsSorted(Map<String, String> params) {
        return params.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }
}
