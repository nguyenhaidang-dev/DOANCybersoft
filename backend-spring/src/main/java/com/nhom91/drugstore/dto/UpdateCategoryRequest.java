package com.nhom91.drugstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryRequest {
    private String name;
    private String description;
    private Boolean isShow;
    private String parentCategory;

    // DTO for update category request
}