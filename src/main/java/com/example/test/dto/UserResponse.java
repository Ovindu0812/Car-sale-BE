package com.example.test.dto;

import com.example.test.entity.Role;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String contactNumber;
    private Role role;
    private Instant createdAt;
    private Instant updatedAt;
}
