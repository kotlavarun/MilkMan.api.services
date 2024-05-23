package com.milkman.model.orders;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegularOrderResponseModel {
    private int orderId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    private int custId;
    private String custName;
    private BigDecimal custOldBal;
    private BigDecimal orderTValue;
    private List<ProductModelForROrder> productList;
    private int isRegularFlag;
}
