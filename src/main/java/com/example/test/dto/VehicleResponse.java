package com.example.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {
    
    private String id;
    
    private String name;
    
    private String type;
    
    private String fuelType;
    
    private Double price;
    
    private Integer year;
    
    private String gearType;
    
    private Integer mileage;
    
    private List<String> images;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Boolean available;
}
