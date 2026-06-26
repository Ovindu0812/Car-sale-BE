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
public class ElectronicCreateRequest {
    
    @NotBlank(message = "Item name is required")
    private String name;
    
    @NotBlank(message = "Item type is required")
    private String type; // Phone, Laptop, Tablet, etc.
    
    private String brand;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    private Double price;
    
    private String condition;
    
    @Size(max = 2000, message = "More details must be 2000 characters or less")
    private String moreDetails;
    
    private List<String> images;

    private Boolean approved;
    private Boolean postedByAdmin;
    private String postedBy;
}
