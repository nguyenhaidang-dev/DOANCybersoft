package com.nhom91.drugstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private String name;
    private Integer qty;
    private String image;
    private BigDecimal price;
    private BigDecimal loanPrice;
    private ProductDTO product;

    // DTO for OrderItem responses
}