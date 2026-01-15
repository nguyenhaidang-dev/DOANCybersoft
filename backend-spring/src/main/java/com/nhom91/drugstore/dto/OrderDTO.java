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
public class OrderDTO {
    private Long id;
    private UserDTO user;
    private String paymentMethod;
    private BigDecimal taxPrice;
    private BigDecimal shippingPrice;
    private BigDecimal totalPrice;
    private String typePay;
    private Boolean isPaid;
    private LocalDateTime paidAt;
    private Boolean isDelivered;
    private LocalDateTime deliveredAt;
    private String status;
    private Boolean isReceive;
    private List<OrderItemDTO> orderItems;
    private ShippingAddressDTO shippingAddress;
    private PaymentResultDTO paymentResult;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}