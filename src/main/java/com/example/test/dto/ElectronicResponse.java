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
public class ElectronicResponse {
    private String id;
    private String name;
    private String type;
    private String brand;
    private Double price;
    private String condition;
    private String moreDetails;
    private List<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean available;
    private Boolean approved;
    private Boolean postedByAdmin;
    private String postedBy;
}
