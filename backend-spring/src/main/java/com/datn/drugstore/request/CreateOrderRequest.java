package com.datn.drugstore.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @NotEmpty(message = "Order items are required")
    @Valid
    private List<OrderItemRequest> orderItems;

    @NotNull(message = "Shipping address is required")
    @Valid
    private ShippingAddressRequest shippingAddress;

    @NotNull(message = "Payment method is required")
    private String paymentMethod;

    private BigDecimal itemsPrice;
    private BigDecimal taxPrice;
    private BigDecimal shippingPrice;
    private BigDecimal totalPrice;
    private String typePay;
    private String status;
    private Boolean isDelivered;
    private Boolean isPaid;
    private String deliveredAt;
    private PaymentResultRequest paymentResult;

}