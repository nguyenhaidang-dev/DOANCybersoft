package com.nhom91.drugstore.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Value("${paypal.client.id:}")
    private String paypalClientId;

    @GetMapping("/paypal")
    public ResponseEntity<Map<String, String>> getPaypalClientId() {
        return ResponseEntity.ok(Map.of("clientId", paypalClientId));
    }

    // Migrated from NodeJS config endpoint
}