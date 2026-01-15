package com.nhom91.drugstore.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBannerRequest {
    private String linkImg;
    private String linkPage;
    private Boolean isShow;
}