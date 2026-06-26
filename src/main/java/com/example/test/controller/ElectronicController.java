package com.example.test.controller;

import com.example.test.dto.ElectronicCreateRequest;
import com.example.test.dto.ElectronicResponse;
import com.example.test.exception.ErrorResponse;
import com.example.test.service.ElectronicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Slf4j
@RestController
@RequestMapping({"/api/electronics", "/electronics"})
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://localhost:5174", "https://carsale-one.vercel.app"})
public class ElectronicController {
    
    private final ElectronicService electronicService;
    
    @PostMapping
    public ResponseEntity<?> saveElectronic(@Valid @RequestBody ElectronicCreateRequest request) {
        try {
            log.info("Received request to save electronic listing: {}", request.getName());
            ElectronicResponse response = electronicService.saveElectronic(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .error("Validation Error")
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .error("Internal Server Error")
                            .message(e.getMessage())
                            .build());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllElectronics() {
        try {
            List<ElectronicResponse> electronics = electronicService.getAllElectronics();
            return ResponseEntity.ok(electronics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getElectronicById(@PathVariable String id) {
        try {
            ElectronicResponse electronic = electronicService.getElectronicById(id);
            return ResponseEntity.ok(electronic);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateElectronic(@PathVariable String id, @Valid @RequestBody ElectronicCreateRequest request) {
        try {
            ElectronicResponse response = electronicService.updateElectronic(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteElectronic(@PathVariable String id) {
        try {
            electronicService.deleteElectronic(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getElectronicsByType(@PathVariable String type) {
        try {
            List<ElectronicResponse> electronics = electronicService.getElectronicsByType(type);
            return ResponseEntity.ok(electronics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
