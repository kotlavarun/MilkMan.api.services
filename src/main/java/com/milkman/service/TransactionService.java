package com.milkman.service;

import com.milkman.model.transaction.CustOrderTransactionRequestModel;
import com.milkman.model.transaction.CustOrderTransactionResponseModel;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    List<CustOrderTransactionResponseModel> generateCustOrderTransByEmpIdAndDate(int empId, LocalDate date);
//    String saveCashTransaction(CustOrderTransactionRequestModel model);
//    String saveAllCashTransaction(List<CustOrderTransactionRequestModel> modelList);
}
