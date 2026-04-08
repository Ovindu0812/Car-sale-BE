package com.example.test.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehicleCreateRequest {

    @NotBlank(message = "Vehicle name/model is required")
    private String name;

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;

    @NotBlank(message = "Gear type is required")
    private String gearType;

    @NotBlank(message = "Fuel type is required")
    private String fuelType;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Year is required")
    @Min(value = 1886, message = "Year must be valid")
    @Max(value = 2100, message = "Year cannot be in the far future")
    private Integer year;

    @NotNull(message = "Mileage is required")
    @PositiveOrZero(message = "Mileage cannot be negative")
    private Integer mileage;
}
