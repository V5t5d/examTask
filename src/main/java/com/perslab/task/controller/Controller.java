package com.perslab.task.controller;

import com.perslab.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@RestController
public class Controller {

    @Autowired
    TaskService service;

    @PostMapping("/{operationId}")
    public ResponseEntity<Map<String, Object>> handleOperation(
            @PathVariable String operationId,
            @RequestBody Map<String, String> params) {

        log.info("request at {} with \"operationId\" = {}", LocalDateTime.now().format(DateTimeFormatter.ISO_TIME), operationId);

        String sortedParams = service.getParamsSorted(params);

        log.debug("params from request : {}", sortedParams);

        String signature = service.getSignature(sortedParams);

        log.debug("generated signature : {}", signature);

        Map<String, Object> responseData = service.getHeaders(signature);

        return ResponseEntity.ok(responseData);
    }



    @GetMapping
    String ping() {
        return "pong";
    }
}
