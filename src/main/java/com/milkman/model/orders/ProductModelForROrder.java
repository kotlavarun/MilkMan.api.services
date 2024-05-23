package com.milkman.model.orders;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductModelForROrder {
    private int orderLineId;
    private int productId;
    private String productCode;
    private BigDecimal productValue;
    private double productQuantity;
    private BigDecimal orderValue;
}
