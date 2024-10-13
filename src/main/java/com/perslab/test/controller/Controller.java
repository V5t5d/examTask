package com.perslab.test.controller;

import com.perslab.test.service.HmacService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class Controller {

    @PostMapping("/{operationId}")
    public ResponseEntity<Map<String, Object>> handleOperation(
            @PathVariable String operationId,
            @RequestBody Map<String, String> params) {

        log.info("request at {} with \"operationId\" = {}", LocalDateTime.now().format(DateTimeFormatter.ISO_TIME), operationId);

        String sortedParams = params.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        log.debug("params from request : {}", sortedParams);

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

        log.debug("generated signature : {}", signature);

        List<Map<String, String>> resultList = List.of(Map.of("signature", signature));

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", "success");
        responseData.put("result", resultList);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    String ping() {
        return "pong";
    }
}
