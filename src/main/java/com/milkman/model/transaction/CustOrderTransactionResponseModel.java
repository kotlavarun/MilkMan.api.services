package com.milkman.model.transaction;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustOrderTransactionResponseModel {
    private int custId;
    private String custName;
    private BigDecimal todayOrderTValue;
    private BigDecimal balance;
    private BigDecimal totalReceivedAmount;
    private List<TransactionEmpModel> transactionList;
}