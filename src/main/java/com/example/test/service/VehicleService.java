package com.example.test.service;

import com.example.test.dto.VehicleCreateRequest;
import com.example.test.dto.VehicleResponse;
import com.example.test.entity.Vehicle;
import com.example.test.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class VehicleService {
    
    private final VehicleRepository vehicleRepository;
    
    public VehicleResponse saveVehicle(VehicleCreateRequest request) {
        try {
            log.info("Starting vehicle save process for vehicle: {}", request.getName());
            
            if (request.getName() != null && !request.getName().trim().isEmpty()) {
                vehicleRepository.findByNameIgnoreCase(request.getName()).ifPresent(v -> {
                    throw new IllegalArgumentException("Vehicle with name '" + request.getName() + "' already exists");
                });
            }
            
            Vehicle vehicle = Vehicle.builder()
                    .name(request.getName())
                    .type(request.getType())
                    .fuelType(request.getFuelType())
                    .price(request.getPrice())
                    .year(request.getYear())
                    .gearType(request.getGearType())
                    .mileage(request.getMileage())
                    .images(request.getImages())
                    .available(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            Vehicle savedVehicle = vehicleRepository.save(vehicle);
            log.info("Vehicle saved successfully with ID: {}", savedVehicle.getId());
            
            return convertToResponse(savedVehicle);
            
        } catch (IllegalArgumentException e) {
            log.warn("Validation error while saving vehicle: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while saving vehicle: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save vehicle: " + e.getMessage(), e);
        }
    }
    
    @Transactional(readOnly = true)
    public List<VehicleResponse> getAllVehicles() {
        try {
            log.info("Fetching all vehicles from database");
            List<Vehicle> vehicles = vehicleRepository.findAll();
            log.info("Retrieved {} vehicles", vehicles.size());
            return vehicles.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching vehicles: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch vehicles: " + e.getMessage(), e);
        }
    }
    
    @Transactional(readOnly = true)
    public VehicleResponse getVehicleById(String id) {
        try {
            log.info("Fetching vehicle with ID: {}", id);
            Vehicle vehicle = vehicleRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Vehicle not found with ID: {}", id);
                        return new IllegalArgumentException("Vehicle not found with ID: " + id);
                    });
            return convertToResponse(vehicle);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error fetching vehicle with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch vehicle: " + e.getMessage(), e);
        }
    }
    
    public VehicleResponse updateVehicle(String id, VehicleCreateRequest request) {
        try {
            log.info("Starting update for vehicle ID: {}", id);
            
            Vehicle vehicle = vehicleRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Vehicle not found for update with ID: {}", id);
                        return new IllegalArgumentException("Vehicle not found with ID: " + id);
                    });
            
            vehicle.setName(request.getName());
            vehicle.setType(request.getType());
            vehicle.setFuelType(request.getFuelType());
            vehicle.setPrice(request.getPrice());
            vehicle.setYear(request.getYear());
            vehicle.setGearType(request.getGearType());
            vehicle.setMileage(request.getMileage());
            vehicle.setImages(request.getImages());
            vehicle.setUpdatedAt(LocalDateTime.now());
            
            Vehicle updatedVehicle = vehicleRepository.save(vehicle);
            log.info("Vehicle updated successfully with ID: {}", updatedVehicle.getId());
            return convertToResponse(updatedVehicle);
            
        } catch (IllegalArgumentException e) {
            log.warn("Validation error while updating vehicle: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error updating vehicle with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update vehicle: " + e.getMessage(), e);
        }
    }
    
    public void deleteVehicle(String id) {
        try {
            log.info("Starting delete for vehicle ID: {}", id);
            
            vehicleRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Vehicle not found for deletion with ID: {}", id);
                        return new IllegalArgumentException("Vehicle not found with ID: " + id);
                    });
            
            vehicleRepository.deleteById(id);
            log.info("Vehicle deleted successfully with ID: {}", id);
            
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting vehicle with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete vehicle: " + e.getMessage(), e);
        }
    }
    
    @Transactional(readOnly = true)
    public List<VehicleResponse> getVehiclesByType(String type) {
        try {
            log.info("Fetching vehicles by type: {}", type);
            List<Vehicle> vehicles = vehicleRepository.findByType(type);
            return vehicles.stream()
                    .map(this::convertToResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching vehicles by type {}: {}", type, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch vehicles: " + e.getMessage(), e);
        }
    }
    
    private VehicleResponse convertToResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .name(vehicle.getName())
                .type(vehicle.getType())
                .fuelType(vehicle.getFuelType())
                .price(vehicle.getPrice())
                .year(vehicle.getYear())
                .gearType(vehicle.getGearType())
                .mileage(vehicle.getMileage())
                .images(vehicle.getImages())
                .createdAt(vehicle.getCreatedAt())
                .updatedAt(vehicle.getUpdatedAt())
                .available(vehicle.getAvailable())
                .build();
    }
}
