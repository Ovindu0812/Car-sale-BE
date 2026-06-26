package com.example.test.service;

import com.example.test.dto.PropertyCreateRequest;
import com.example.test.dto.PropertyResponse;
import com.example.test.entity.Property;
import com.example.test.repository.PropertyRepository;
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
public class PropertyService {
    
    private final PropertyRepository propertyRepository;
    
    public PropertyResponse saveProperty(PropertyCreateRequest request) {
        try {
            log.info("Starting property save process: {}", request.getName());
            
            if (request.getName() != null && !request.getName().trim().isEmpty()) {
                propertyRepository.findByNameIgnoreCase(request.getName()).ifPresent(p -> {
                    throw new IllegalArgumentException("Property with name '" + request.getName() + "' already exists");
                });
            }
            
            Property property = Property.builder()
                    .name(request.getName())
                    .type(request.getType())
                    .price(request.getPrice())
                    .district(request.getDistrict())
                    .moreDetails(request.getMoreDetails())
                    .images(request.getImages())
                    .approved(request.getApproved() != null ? request.getApproved() : false)
                    .postedByAdmin(request.getPostedByAdmin() != null ? request.getPostedByAdmin() : false)
                    .postedBy(request.getPostedBy())
                    .available(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            Property savedProperty = propertyRepository.save(property);
            log.info("Property saved successfully with ID: {}", savedProperty.getId());
            
            return convertToResponse(savedProperty);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save property: " + e.getMessage(), e);
        }
    }
    
    @Transactional(readOnly = true)
    public List<PropertyResponse> getAllProperties() {
        return propertyRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PropertyResponse getPropertyById(String id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + id));
        return convertToResponse(property);
    }
    
    public PropertyResponse updateProperty(String id, PropertyCreateRequest request) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + id));
        
        property.setName(request.getName());
        property.setType(request.getType());
        property.setPrice(request.getPrice());
        property.setDistrict(request.getDistrict());
        property.setMoreDetails(request.getMoreDetails());
        property.setImages(request.getImages());
        if (request.getApproved() != null) {
            property.setApproved(request.getApproved());
        }
        property.setUpdatedAt(LocalDateTime.now());
        
        Property updatedProperty = propertyRepository.save(property);
        return convertToResponse(updatedProperty);
    }
    
    public void deleteProperty(String id) {
        propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Property not found with ID: " + id));
        propertyRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<PropertyResponse> getPropertiesByType(String type) {
        return propertyRepository.findByType(type).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    private PropertyResponse convertToResponse(Property property) {
        return PropertyResponse.builder()
                .id(property.getId())
                .name(property.getName())
                .type(property.getType())
                .price(property.getPrice())
                .district(property.getDistrict())
                .moreDetails(property.getMoreDetails())
                .images(property.getImages())
                .createdAt(property.getCreatedAt())
                .updatedAt(property.getUpdatedAt())
                .available(property.getAvailable())
                .approved(property.getApproved())
                .postedByAdmin(property.getPostedByAdmin())
                .postedBy(property.getPostedBy())
                .build();
    }
}
