package com.milkman.model.orders;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PPriceModel {
    private int productId;
    private BigDecimal productValue;
}
