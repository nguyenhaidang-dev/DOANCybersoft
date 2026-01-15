package com.nhom91.drugstore.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePdfRequest {
    private String name;
    private String image;
    private String file;
}