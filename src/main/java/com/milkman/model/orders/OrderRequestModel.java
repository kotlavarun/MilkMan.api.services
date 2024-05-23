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
public class OrderRequestModel {
    private int custId;
    private String custName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    private BigDecimal orderTValue;
    private int isRegularOrder;
    private String remark;
    private List<ProductModelForOrderLine> productList;
}
