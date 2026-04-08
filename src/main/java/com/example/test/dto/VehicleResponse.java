package com.example.test.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class VehicleResponse {
    private String id;
    private String name;
    private String vehicleType;
    private String gearType;
    private String fuelType;
    private BigDecimal price;
    private Integer year;
    private Integer mileage;
    // Base64 data URIs for images (e.g. data:image/png;base64,...) 
    private List<String> photos;
    private Instant createdAt;
}
