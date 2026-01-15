package com.nhom91.drugstore.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePdfRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Image is required")
    private String image;

    @NotBlank(message = "File is required")
    private String file;

}