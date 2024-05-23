package com.milkman.service;

import com.milkman.entity.CustomerProductPriceEntity;
import com.milkman.model.CustomerPriceForProduct;

import java.util.List;

public interface CustomerProductPriceService {
    void addNewCustomerPrices(List<CustomerProductPriceEntity> listEntity);
    String updatedCustomerNewProductPrice(CustomerPriceForProduct newPriceData);
    List<CustomerPriceForProduct> getProductPListByCustId(int custId);
}
