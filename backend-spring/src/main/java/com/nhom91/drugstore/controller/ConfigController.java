package com.nhom91.drugstore.controller;

import com.nhom91.drugstore.response.BaseResponse;
import com.nhom91.drugstore.utils.ResponseFactory;
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
    public ResponseEntity<BaseResponse> getPaypalClientId() {
        return ResponseFactory.success(Map.of("clientId", paypalClientId));
    }

}