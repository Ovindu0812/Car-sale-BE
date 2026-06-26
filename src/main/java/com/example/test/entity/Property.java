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
@Document(collection = "properties")
public class Property {
    
    @Id
    private String id;
    
    private String name;
    private String type; // Land, House, Apartment
    private Double price;
    private String district;
    private String moreDetails;
    private List<String> images;
    private Boolean approved;
    private Boolean postedByAdmin;
    private String postedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean available;
}
