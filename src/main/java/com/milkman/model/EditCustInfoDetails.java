package com.milkman.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class EditCustInfoDetails {
    private int custId;
    private String custName;
    private String custShopName;
    private String phone;
    private int routeId;
    private int empId;
}
