package com.nhom91.drugstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "payment_id")
    private String paymentId;

    private String status;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "email_address")
    private String emailAddress;

}