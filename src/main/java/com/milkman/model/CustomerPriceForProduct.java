package com.milkman.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerPriceForProduct {
    private int custId;
    private int productId;
    private BigDecimal price;
}
