package com.milkman.controller;

import com.milkman.model.transaction.CustOrderTransactionRequestModel;
import com.milkman.model.transaction.CustOrderTransactionResponseModel;
import com.milkman.model.transaction.DateEmpIdModel;
import com.milkman.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @RequestMapping(value = "/getByOrderBased", method = RequestMethod.POST)
    public ResponseEntity<?> newSingleOrderRequest(@RequestBody DateEmpIdModel model) {
        List<CustOrderTransactionResponseModel> response = transactionService.generateCustOrderTransByEmpIdAndDate(model.getEmpId(), model.getDate().toLocalDate());
        if(!response.isEmpty()){
          return ResponseEntity.ok().body(response);
        }
       return ResponseEntity.badRequest().body("Service error in transaction");
    }

//    @RequestMapping(value = "/saveCashTrans", method = RequestMethod.POST)
//    public ResponseEntity<?> saveCashTransaction(@RequestBody CustOrderTransactionRequestModel model) {
//        String response = transactionService.saveCashTransaction(model);
//        if(!response.isEmpty()){
//            return ResponseEntity.ok().body(response);
//        }
//        return ResponseEntity.badRequest().body("Cash Entry could not be saved");
//    }
//    @RequestMapping(value = "/saveAllCashTrans", method = RequestMethod.POST)
//    public ResponseEntity<?> saveALLCashTransaction(@RequestBody List<CustOrderTransactionRequestModel> modelList) {
//        String response = transactionService.saveAllCashTransaction(modelList);
//        if(!response.isEmpty()){
//            return ResponseEntity.ok().body(response);
//        }
//        return ResponseEntity.badRequest().body("Cash Entry could not be saved");
//    }
}
