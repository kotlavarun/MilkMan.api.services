package com.milkman.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewCustDetailsModel {
    private String custName;
    private String custShopName;
    private String phone;
    private int routeId;
    private int empId;
    private List<CustomerPriceForProduct> priceList;
}
