package com.nhom91.drugstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Quantity is required")
    private Integer qty;

    @NotBlank(message = "Image is required")
    private String image;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "Loan price is required")
    private BigDecimal loanPrice;

    @NotNull(message = "Product ID is required")
    private Long product;

    // DTO for order item request
}