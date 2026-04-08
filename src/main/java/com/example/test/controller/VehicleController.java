package com.example.test.controller;

import com.example.test.dto.VehicleCreateRequest;
import com.example.test.dto.VehicleResponse;
import com.example.test.exception.ErrorResponse;
import com.example.test.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://localhost:5174"})
public class VehicleController {
    
    private final VehicleService vehicleService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveVehicle(@Valid @RequestBody VehicleCreateRequest request) {
        try {
            log.info("Received request to save vehicle: {}", request.getName());
            VehicleResponse response = vehicleService.saveVehicle(request);
            log.info("Vehicle saved successfully with ID: {}", response.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            log.warn("Validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .error("Validation Error")
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            log.error("Error saving vehicle: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .error("Internal Server Error")
                            .message("An error occurred while saving the vehicle: " + e.getMessage())
                            .build());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllVehicles() {
        try {
            log.info("Received request to get all vehicles");
            List<VehicleResponse> vehicles = vehicleService.getAllVehicles();
            return ResponseEntity.ok(vehicles);
        } catch (Exception e) {
            log.error("Error fetching vehicles: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .error("Internal Server Error")
                            .message("An error occurred while fetching vehicles")
                            .build());
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable String id) {
        try {
            log.info("Received request to get vehicle with ID: {}", id);
            VehicleResponse vehicle = vehicleService.getVehicleById(id);
            return ResponseEntity.ok(vehicle);
        } catch (IllegalArgumentException e) {
            log.warn("Vehicle not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.NOT_FOUND.value())
                            .error("Not Found")
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            log.error("Error fetching vehicle: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .error("Internal Server Error")
                            .message("An error occurred while fetching the vehicle")
                            .build());
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateVehicle(@PathVariable String id, @Valid @RequestBody VehicleCreateRequest request) {
        try {
            log.info("Received request to update vehicle with ID: {}", id);
            VehicleResponse response = vehicleService.updateVehicle(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("Validation error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .error("Validation Error")
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            log.error("Error updating vehicle: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .error("Internal Server Error")
                            .message("An error occurred while updating the vehicle")
                            .build());
        }
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteVehicle(@PathVariable String id) {
        try {
            log.info("Received request to delete vehicle with ID: {}", id);
            vehicleService.deleteVehicle(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            log.warn("Vehicle not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.NOT_FOUND.value())
                            .error("Not Found")
                            .message(e.getMessage())
                            .build());
        } catch (Exception e) {
            log.error("Error deleting vehicle: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .error("Internal Server Error")
                            .message("An error occurred while deleting the vehicle")
                            .build());
        }
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<?> getVehiclesByType(@PathVariable String type) {
        try {
            log.info("Received request to get vehicles by type: {}", type);
            List<VehicleResponse> vehicles = vehicleService.getVehiclesByType(type);
            return ResponseEntity.ok(vehicles);
        } catch (Exception e) {
            log.error("Error fetching vehicles by type: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponse.builder()
                            .timestamp(Instant.now())
                            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .error("Internal Server Error")
                            .message("An error occurred while fetching vehicles")
                            .build());
        }
    }
}
