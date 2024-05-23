package com.milkman.model.orders;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustDetailsAndPPriceListModel {
    private int custId;
    private String custName;
    private BigDecimal custOldBal;
    private List<PPriceModel> productPriceList;
}