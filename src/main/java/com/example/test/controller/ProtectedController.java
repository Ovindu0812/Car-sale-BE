package com.example.test.controller;

import com.example.test.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

    @GetMapping("/hello")
    public ResponseEntity<ApiResponse> hello() {
        return ResponseEntity.ok(ApiResponse.builder().message("Hello, authenticated user").build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<ApiResponse> admin() {
        return ResponseEntity.ok(ApiResponse.builder().message("Hello, admin").build());
    }
}
