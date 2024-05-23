package com.milkman.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UpdateProductMRPModel {
    private int productId;
    private BigDecimal productMRP;
    private BigDecimal boxPrice;
}
