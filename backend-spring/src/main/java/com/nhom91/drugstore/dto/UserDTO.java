package com.nhom91.drugstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Boolean isAdmin;
    private LocalDateTime createdAt;
    private String token;

    // DTO for User responses, migrated from NodeJS user responses
}