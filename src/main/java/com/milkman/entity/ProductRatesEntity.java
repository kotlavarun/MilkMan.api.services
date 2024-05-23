package com.milkman.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "product_rates", schema = "sales")
public class ProductRatesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pr_id")
    private int prId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_mrp")
    private BigDecimal productMRP;

    @Column(name = "box_price")
    private BigDecimal boxPrice;

    @Column(name = "created_on")
    private LocalDateTime createOn;

    @Column(name = "is_active")
    private int isActive;

}
