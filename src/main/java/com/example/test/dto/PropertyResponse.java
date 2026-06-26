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
public class PropertyResponse {
    private String id;
    private String name;
    private String type;
    private Double price;
    private String district;
    private String moreDetails;
    private List<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean available;
    private Boolean approved;
    private Boolean postedByAdmin;
    private String postedBy;
}
