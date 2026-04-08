package com.example.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleCreateRequest {
    
    @NotBlank(message = "Vehicle name is required")
    private String name;
    
    @NotBlank(message = "Vehicle type is required")
    private String type;
    
    @NotBlank(message = "Fuel type is required")
    private String fuelType;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;
    
    @NotNull(message = "Year is required")
    @Min(value = 1900, message = "Year must be 1900 or later")
    @Max(value = 2100, message = "Year cannot be in the future")
    private Integer year;
    
    @NotBlank(message = "Gear type is required")
    private String gearType;
    
    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;
    
    @NotNull(message = "Images are required")
    @Size(min = 1, message = "At least one image is required")
    private List<String> images;
}
