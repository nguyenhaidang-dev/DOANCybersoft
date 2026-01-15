package com.datn.drugstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BannerDTO {
    private Long id;
    private String linkImg;
    private String linkPage;
    private Boolean isShow;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}