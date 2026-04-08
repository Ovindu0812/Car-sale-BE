package com.example.test.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "vehicles")
public class Vehicle {
    
    @Id
    private String id;
    
    private String name;
    
    private String type;
    
    private String fuelType;
    
    private Double price;
    
    private Integer year;
    
    private String gearType;
    
    private Integer mileage;

    private String condition;

    private String moreDetails;
    
    private List<String> images;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Boolean available;
}
