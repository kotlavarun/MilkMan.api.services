package com.milkman.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkman.entity.CustomerProductPriceEntity;
import com.milkman.model.CustomerPriceForProduct;
import com.milkman.repo.CustomerProductPriceRepo;
import com.milkman.util.DataValidator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerProductPriceServiceImpl implements CustomerProductPriceService {
    @Autowired
    CustomerProductPriceRepo customerProductPriceRepo;
    @Autowired
    ObjectMapper mapper;

    @Override
    public void addNewCustomerPrices(List<CustomerProductPriceEntity> listEntity) {
        customerProductPriceRepo.saveAll(listEntity);
    }

    @Transactional
    @Override
    public String updatedCustomerNewProductPrice(CustomerPriceForProduct newPriceData) {
        if (!DataValidator.isEmptyOrNullValidator(newPriceData.getProductId()) && !DataValidator.isEmptyOrNullValidator(newPriceData.getCustId())) {
            customerProductPriceRepo.deactivateProductOldPrice(newPriceData.getProductId(), newPriceData.getCustId());
            customerProductPriceRepo.saveNewRecord(newPriceData.getProductId(), newPriceData.getPrice(), newPriceData.getCustId());
            return "New price details have been updated";
        }
        return "Price list have not been updated please provide valid data";
    }

    @Override
    public List<CustomerPriceForProduct> getProductPListByCustId(int custId) {
        List<Object[]> dbResponse = customerProductPriceRepo.getProductPListByCustId(custId);
        if (!dbResponse.isEmpty()) {
            List<CustomerPriceForProduct> priceList = new ArrayList<>();
            for (Object[] obj : dbResponse) {
                CustomerPriceForProduct model = new CustomerPriceForProduct();
                model.setCustId((Integer) obj[0]);
                model.setProductId((Integer) obj[1]);
                model.setPrice(new BigDecimal(String.valueOf(obj[2])));
                priceList.add(model);
            }
            return priceList;
        }
        return null;
    }
}

