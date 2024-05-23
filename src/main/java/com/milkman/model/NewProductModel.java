package com.milkman.model;

import com.milkman.constant.ConstantVariable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class NewProductModel {
    private String productName;
    private String productCode;
    private String productDesc;
    private BigDecimal productMRP;
    private double boxQuantity;
    private BigDecimal boxPrice;
}
