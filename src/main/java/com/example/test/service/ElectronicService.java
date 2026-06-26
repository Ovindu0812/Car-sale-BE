package com.example.test.service;

import com.example.test.dto.ElectronicCreateRequest;
import com.example.test.dto.ElectronicResponse;
import com.example.test.entity.Electronic;
import com.example.test.repository.ElectronicRepository;
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
public class ElectronicService {
    
    private final ElectronicRepository electronicRepository;
    
    public ElectronicResponse saveElectronic(ElectronicCreateRequest request) {
        try {
            log.info("Starting electronic save process: {}", request.getName());
            
            if (request.getName() != null && !request.getName().trim().isEmpty()) {
                electronicRepository.findByNameIgnoreCase(request.getName()).ifPresent(e -> {
                    throw new IllegalArgumentException("Electronic listing with name '" + request.getName() + "' already exists");
                });
            }
            
            Electronic electronic = Electronic.builder()
                    .name(request.getName())
                    .type(request.getType())
                    .brand(request.getBrand())
                    .price(request.getPrice())
                    .condition(request.getCondition())
                    .moreDetails(request.getMoreDetails())
                    .images(request.getImages())
                    .approved(request.getApproved() != null ? request.getApproved() : false)
                    .postedByAdmin(request.getPostedByAdmin() != null ? request.getPostedByAdmin() : false)
                    .postedBy(request.getPostedBy())
                    .available(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            Electronic savedElectronic = electronicRepository.save(electronic);
            log.info("Electronic saved successfully with ID: {}", savedElectronic.getId());
            
            return convertToResponse(savedElectronic);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save electronic listing: " + e.getMessage(), e);
        }
    }
    
    @Transactional(readOnly = true)
    public List<ElectronicResponse> getAllElectronics() {
        return electronicRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ElectronicResponse getElectronicById(String id) {
        Electronic electronic = electronicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Electronic listing not found with ID: " + id));
        return convertToResponse(electronic);
    }
    
    public class ValidationException extends RuntimeException {
        public ValidationException(String message) {
            super(message);
        }
    }

    public ElectronicResponse updateElectronic(String id, ElectronicCreateRequest request) {
        Electronic electronic = electronicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Electronic listing not found with ID: " + id));
        
        electronic.setName(request.getName());
        electronic.setType(request.getType());
        electronic.setBrand(request.getBrand());
        electronic.setPrice(request.getPrice());
        electronic.setCondition(request.getCondition());
        electronic.setMoreDetails(request.getMoreDetails());
        electronic.setImages(request.getImages());
        if (request.getApproved() != null) {
            electronic.setApproved(request.getApproved());
        }
        electronic.setUpdatedAt(LocalDateTime.now());
        
        Electronic updatedElectronic = electronicRepository.save(electronic);
        return convertToResponse(updatedElectronic);
    }
    
    public void deleteElectronic(String id) {
        electronicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Electronic listing not found with ID: " + id));
        electronicRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<ElectronicResponse> getElectronicsByType(String type) {
        return electronicRepository.findByType(type).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    private ElectronicResponse convertToResponse(Electronic electronic) {
        return ElectronicResponse.builder()
                .id(electronic.getId())
                .name(electronic.getName())
                .type(electronic.getType())
                .brand(electronic.getBrand())
                .price(electronic.getPrice())
                .condition(electronic.getCondition())
                .moreDetails(electronic.getMoreDetails())
                .images(electronic.getImages())
                .createdAt(electronic.getCreatedAt())
                .updatedAt(electronic.getUpdatedAt())
                .available(electronic.getAvailable())
                .approved(electronic.getApproved())
                .postedByAdmin(electronic.getPostedByAdmin())
                .postedBy(electronic.getPostedBy())
                .build();
    }
}
