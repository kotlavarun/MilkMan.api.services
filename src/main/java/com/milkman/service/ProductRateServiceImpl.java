package com.milkman.service;

import com.milkman.entity.ProductRatesEntity;
import com.milkman.model.UpdateProductMRPModel;
import com.milkman.repo.ProductRatesRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductRateServiceImpl implements ProductRateService{
    @Autowired
    ProductRatesRepo productRatesRepo;

    @Override
    public void deactivteProductMRPDetails(int productId) {
        productRatesRepo.deActivateProductMRPRecord(productId);
    }

    //return new product rate id
    @Override
    public int addNewProductMRPDetails(ProductRatesEntity data) {
        return productRatesRepo.save(data).getPrId();
    }

    @Transactional
    @Override
    public String updateProductMRPDetails(UpdateProductMRPModel model) {
        deactivteProductMRPDetails(model.getProductId());
        ProductRatesEntity newEntry = new ProductRatesEntity();
        newEntry.setProductId(model.getProductId());
        newEntry.setProductMRP(model.getProductMRP());
        newEntry.setBoxPrice(model.getBoxPrice());
        newEntry.setIsActive(1);
        int result = addNewProductMRPDetails(newEntry);
        if(result > 0){
            return "Product MRP details updated successfully";
        }
        return "Product details not updated";
    }
}
