package com.milkman.model.orders;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductModelForOrderLine {
    private int productId;
    private double productQuantity;
    private BigDecimal productUnitValue;
    private BigDecimal orderValue;
}


