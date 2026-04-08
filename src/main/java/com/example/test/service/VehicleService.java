package com.example.test.service;

import com.example.test.dto.VehicleCreateRequest;
import com.example.test.dto.VehicleResponse;
import com.example.test.entity.Photo;
import com.example.test.entity.Vehicle;
import com.example.test.exception.VehicleNotFoundException;
import com.example.test.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleResponse createVehicle(VehicleCreateRequest request, List<MultipartFile> photos) {
        if (photos != null && photos.size() > 10) {
            throw new IllegalArgumentException("Cannot upload more than 10 photos per vehicle.");
        }

        List<Photo> photoEntities = null;
        if (photos != null) {
            photoEntities = photos.stream()
                    .filter(p -> p != null && !p.isEmpty())
                    .map(p -> {
                        try {
                            return Photo.builder()
                                    .fileName(p.getOriginalFilename())
                                    .contentType(p.getContentType())
                                    .data(p.getBytes())
                                    .build();
                        } catch (Exception ex) {
                            throw new RuntimeException("Failed to read uploaded photo", ex);
                        }
                    }).collect(Collectors.toList());
        }

        Vehicle vehicle = Vehicle.builder()
                .name(request.getName())
                .vehicleType(request.getVehicleType())
                .gearType(request.getGearType())
                .fuelType(request.getFuelType())
                .price(request.getPrice())
                .year(request.getYear())
                .mileage(request.getMileage())
                .photos(photoEntities)
                .build();

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return mapToResponse(savedVehicle);
    }

    public VehicleResponse getVehicleById(String id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with id: " + id));
        return mapToResponse(vehicle);
    }

    public VehicleResponse updateVehicle(String id, VehicleCreateRequest request, List<MultipartFile> photos) {
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with id: " + id));

        if (photos != null && photos.size() > 10) {
            throw new IllegalArgumentException("Cannot upload more than 10 photos per vehicle.");
        }

        existing.setName(request.getName());
        existing.setVehicleType(request.getVehicleType());
        existing.setGearType(request.getGearType());
        existing.setFuelType(request.getFuelType());
        existing.setPrice(request.getPrice());
        existing.setYear(request.getYear());
        existing.setMileage(request.getMileage());

        if (photos != null && !photos.isEmpty()) {
            List<Photo> photoEntities = photos.stream()
                    .filter(p -> p != null && !p.isEmpty())
                    .map(p -> {
                        try {
                            return Photo.builder()
                                    .fileName(p.getOriginalFilename())
                                    .contentType(p.getContentType())
                                    .data(p.getBytes())
                                    .build();
                        } catch (Exception ex) {
                            throw new RuntimeException("Failed to read uploaded photo", ex);
                        }
                    }).collect(Collectors.toList());

            existing.setPhotos(photoEntities);
        }

        Vehicle saved = vehicleRepository.save(existing);
        return mapToResponse(saved);
    }

    public void deleteVehicle(String id) {
        Vehicle existing = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found with id: " + id));
        vehicleRepository.delete(existing);
    }

    public List<VehicleResponse> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private VehicleResponse mapToResponse(Vehicle vehicle) {
        List<String> photoDataUris = null;
        if (vehicle.getPhotos() != null) {
            photoDataUris = vehicle.getPhotos().stream().map(photo -> {
                String base64 = Base64.getEncoder().encodeToString(photo.getData());
                String contentType = photo.getContentType() != null ? photo.getContentType() : "application/octet-stream";
                return "data:" + contentType + ";base64," + base64;
            }).collect(Collectors.toList());
        }

        return VehicleResponse.builder()
                .id(vehicle.getId())
                .name(vehicle.getName())
                .vehicleType(vehicle.getVehicleType())
                .gearType(vehicle.getGearType())
                .fuelType(vehicle.getFuelType())
                .price(vehicle.getPrice())
                .year(vehicle.getYear())
                .mileage(vehicle.getMileage())
                .photos(photoDataUris)
                .createdAt(vehicle.getCreatedAt())
                .build();
    }
}
