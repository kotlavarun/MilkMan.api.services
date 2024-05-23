package com.milkman.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkman.entity.TransactionMasterEntity;
import com.milkman.model.transaction.CustOrderTransactionRequestModel;
import com.milkman.model.transaction.CustOrderTransactionResponseModel;
import com.milkman.model.transaction.TransactionEmpModel;
import com.milkman.repo.CustomerBalanceDetailsRepo;
import com.milkman.repo.TransactionMasterRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    TransactionMasterRepo transactionMasterRepo;
    @Autowired
    CustomerBalanceDetailsRepo customerBalanceDetailsRepo;
    @Autowired
    ObjectMapper mapper;

    @Override
    public List<CustOrderTransactionResponseModel> generateCustOrderTransByEmpIdAndDate(int empId, LocalDate date) {
        List<CustOrderTransactionResponseModel> responseList = new ArrayList<>();
        List<Object> dbResponse = transactionMasterRepo.generateTransListBasedOnEmpIdAndDate(empId, date);
        for (Object obj : dbResponse){
            CustOrderTransactionResponseModel model = new CustOrderTransactionResponseModel();
            try {
                model = mapper.readValue(obj.toString(), CustOrderTransactionResponseModel.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            if(model != null){
                for(TransactionEmpModel tranEmpModel : model.getTransactionList()){
                    model.setTotalReceivedAmount(model.getTotalReceivedAmount().add(tranEmpModel.getReceivedAmount()));
                }
            }
            responseList.add(model);
        }
        return responseList;
    }

//    @Transactional
//    @Override
//    public String saveCashTransaction(CustOrderTransactionRequestModel model) {
//        if(model.getTransactionList().get(0).getReceivedAmount().compareTo()){
//
//        }
//        if(model.getEmpId() > 0){
//            if(model.getOrderId() > 0 || model.getCustId() > 0){
//                TransactionMasterEntity entity = new TransactionMasterEntity();
//                if(model.getOrderId() > 0){
//                    entity.setOrderId(model.getOrderId());
//                }
//                entity.setCustId(model.getCustId());
//                entity.setEmpId(model.getEmpId());
//                entity.setReceivedAmount(model.getReceivedAmount());
//                entity.setRemark(model.getRemark());
//                transactionMasterRepo.save(entity);
//                customerBalanceDetailsRepo.updateCustBalance(model.getCustId(), model.getBalance().subtract(model.getReceivedAmount()));
//                return "Cash Transaction record successfully";
//            } else {
//                return "Order and Customer details are empty for cash transaction";
//            }
//        } else {
//            return "Employee Details are empty for cash transaction";
//        }
//        return null;
//    }

//    @Override
//    public String saveAllCashTransaction(List<CustOrderTransactionRequestModel> modelList) {
//        if(!modelList.isEmpty()){
//            for(CustOrderTransactionRequestModel model : modelList){
//                saveCashTransaction(model);
//            }
//            return "All Cash transaction recorded successfully";
//        } else {
//            return "Cash transaction list empty";
//        }
//    }
}
