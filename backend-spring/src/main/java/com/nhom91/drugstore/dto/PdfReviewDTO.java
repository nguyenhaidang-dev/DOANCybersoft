package com.nhom91.drugstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfReviewDTO {
    private Long id;
    private Integer rating;
    private String comment;
    private String userName;
    private Long userId;
    private LocalDateTime createdAt;

    // DTO for PdfReview responses
}