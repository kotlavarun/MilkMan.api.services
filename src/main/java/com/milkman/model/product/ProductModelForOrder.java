package com.milkman.model.product;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductModelForOrder {
    private int productId;
    private String productName;
    private String productCode;
    private BigDecimal productMRP;
}
