package com.milkman.service;

import com.milkman.entity.ProductRatesEntity;
import com.milkman.model.UpdateProductMRPModel;

public interface ProductRateService {
    int addNewProductMRPDetails(ProductRatesEntity data);
    void deactivteProductMRPDetails(int productId);
    String updateProductMRPDetails(UpdateProductMRPModel model);
}
