package com.datn.drugstore.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBannerRequest {
    @NotBlank(message = "Link image is required")
    private String linkImg;

    private String linkPage;

    private Boolean isShow;

}