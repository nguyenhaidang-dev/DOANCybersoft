package com.nhom91.drugstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfDTO {
    private Long id;
    private String name;
    private String image;
    private String file;
    private List<PdfReviewDTO> reviews;
    private BigDecimal rating;
    private Integer numReviews;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // DTO for Pdf responses
}