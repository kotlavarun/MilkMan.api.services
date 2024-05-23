package com.milkman.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerSearchModel {
    private int custId;
    private String custName;
    private String custShopName;
    private String phone;
}
