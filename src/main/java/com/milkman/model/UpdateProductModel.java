package com.milkman.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UpdateProductModel {
    private int productId;
    private String productName;
    private String productCode;
    private String productDesc;
    private double boxQuantity;

}
