package com.nhom91.drugstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    private Boolean isShow;

    private String parentCategory;

    // DTO for create category request
}