package com.milkman.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "product_display_details", schema = "sales")
public class ProductDisplayDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pdd_id")
    private int productDisplayId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "display_order")
    private int displayOrder;

}
