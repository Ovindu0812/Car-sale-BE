package com.example.test.controller;

import com.example.test.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @GetMapping("/")
    public ResponseEntity<ApiResponse> root() {
        return ResponseEntity.ok(ApiResponse.builder().message("Car Sale Backend API is running").build());
    }
}
