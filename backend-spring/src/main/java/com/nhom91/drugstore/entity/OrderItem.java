package com.nhom91.drugstore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer qty;

    @Column(nullable = false)
    private String image;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "loan_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal loanPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}