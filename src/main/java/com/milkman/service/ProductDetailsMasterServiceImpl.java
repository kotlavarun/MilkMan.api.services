package com.milkman.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkman.entity.ProductDetailsMasterEntity;
import com.milkman.entity.ProductRatesEntity;
import com.milkman.model.NewProductModel;
import com.milkman.model.UpdateProductModel;
import com.milkman.model.product.ProductModelForOrder;
import com.milkman.repo.ProductDetailsMasterRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDetailsMasterServiceImpl implements ProductDetailsMasterService {

    @Autowired
    ProductDetailsMasterRepo productDetailsMasterRepo;
    @Autowired
    ProductRateService productRateService;
    @Autowired
    ObjectMapper mapper;

    @Override
    @Transactional
    public String addNewProduct(NewProductModel newModel) {
        ProductDetailsMasterEntity entity = new ProductDetailsMasterEntity();
        entity.setProductCode(newModel.getProductCode());
        entity.setProductName(newModel.getProductName());
        entity.setProductDesc(newModel.getProductDesc());
        entity.setBoxQuantity(newModel.getBoxQuantity());
        entity.setIsActive(1);
        int newProductId = productDetailsMasterRepo.save(entity).getProductId();
        ProductRatesEntity ratesEntity = new ProductRatesEntity();
        ratesEntity.setProductId(newProductId);
        ratesEntity.setProductMRP(newModel.getProductMRP());
        ratesEntity.setBoxPrice(newModel.getBoxPrice());
        ratesEntity.setIsActive(1);
        if(productRateService.addNewProductMRPDetails(ratesEntity) > 0){
            return "New product ("+entity.getProductCode()+") "+entity.getProductName()+" have been added to list";
        }
        return null;
    }

    @Transactional
    @Override
    public String updateProductDetails(UpdateProductModel updateModel) {
        int result = productDetailsMasterRepo.updateProductDetails(updateModel.getProductId(),updateModel.getProductCode(),updateModel.getProductName(),updateModel.getProductDesc(),updateModel.getBoxQuantity());
        if(result > 0){
            return updateModel.getProductName() +"("+updateModel.getProductCode()+")"+" have been updated successfully";
        } else {
            return "Product details have not been updated contact admin";
        }
    }

    @Transactional
    @Override
    public String deleteProductDetails(int productId) {
        int result = productDetailsMasterRepo.deactivateProduct(productId);
        productRateService.deactivteProductMRPDetails(productId);
        if(result == 1){
            return "Product have been deleted from list";
        }
        return "product have not been ";
    }

    @Override
    public List<ProductModelForOrder> getActiveProductList() {
        List<ProductModelForOrder> responseModel = new ArrayList<>();
        List<Object> dbResponse = productDetailsMasterRepo.getActiveProductList();
        for (Object obj : dbResponse){
            ProductModelForOrder model = new ProductModelForOrder();
            try {
                model = mapper.readValue(obj.toString(), ProductModelForOrder.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            responseModel.add(model);
        }
        return responseModel;
    }

}
