package com.nhom91.drugstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResultRequest {
    private String paymentId;
    private String status;
    private String updateTime;
    private String emailAddress;

    // DTO for PaymentResult requests
}