package com.milkman.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milkman.constant.ConstantVariable;
import com.milkman.entity.OrderLinesEntity;
import com.milkman.entity.OrderMasterEntity;
import com.milkman.model.orders.*;
import com.milkman.repo.*;
import com.milkman.util.DataValidator;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderRequestHandleServiceImpl implements OrderRequestHandleService{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    OrderMasterRepo orderMasterRepo;
    @Autowired
    OrderLinesRepo orderLinesRepo;
    @Autowired
    CustomerProductPriceRepo customerProductPriceRepo;
    @Autowired
    ProductRatesRepo productRatesRepo;
    @Autowired
    ProductDisplayDetailsRepo productDisplayDetailsRepo;
    @Autowired
    CustomerBalanceDetailsRepo customerBalanceDetailsRepo;
    @Autowired
    ObjectMapper mapper;
    /** convertProductModelToEntity method converts List<ProductModelForOrderLine> to
        List<OrderLinesEntity>
    */
    private List<OrderLinesEntity> convertProductModelToEntity(List<ProductModelForOrderLine> orderLineList, int orderId){
        List<OrderLinesEntity> newOrderLinesList = new ArrayList<>();
        for(ProductModelForOrderLine model : orderLineList){
            OrderLinesEntity entity = new OrderLinesEntity();
            entity.setOrderId(orderId);
            entity.setProductId(model.getProductId());
            entity.setQuantity(model.getProductQuantity());
            entity.setProductUnitPrice(model.getProductUnitValue());
            entity.setOrderValue(model.getOrderValue());
            newOrderLinesList.add(entity);
        }
        return newOrderLinesList;
    }

    /**
     *   singleOrderRequestHandler method used to save non regular order (Single Cust order)
     */
    @Transactional
    @Override
    public String singleOrderRequestHandler(OrderRequestModel requestModel) {
        OrderMasterEntity newOrder = new OrderMasterEntity();
        newOrder.setOrderDate(requestModel.getOrderDate());
        newOrder.setCustId(requestModel.getCustId());
        newOrder.setOrderTotalValue(requestModel.getOrderTValue());
        newOrder.setIsRegularOrder(requestModel.getIsRegularOrder());
        newOrder.setRemark(requestModel.getRemark());
        int newOrderId = orderMasterRepo.save(newOrder).getOrderId();
        if(DataValidator.isEmptyOrNullValidator(newOrderId)){
            orderLinesRepo.saveAll(convertProductModelToEntity(requestModel.getProductList(), newOrderId));

            return ConstantVariable.OrderSuccessStatus;
        } else {
            return ConstantVariable.OrderNotPlacesMessage;
        }
    }

    @Override
    public List<RegularOrderResponseModel> getRegularOrderByDate(LocalDateTime date, int routeId) {
        List<ProductModelForROrder> productModelList = getProductListWithBasePrice();
        List<CustDetailsAndPPriceListModel> custProductPriceList = getCustProductPriceList(routeId);
        List<RegularOrderResponseModel> rOrderResponseList = new ArrayList<>();
        for(CustDetailsAndPPriceListModel model : custProductPriceList){
            RegularOrderResponseModel rModel = new RegularOrderResponseModel();
            RegularOrderResponseModel orderDetails =  getCustROrderDetails(date, model.getCustId());
            rModel.setCustId(model.getCustId());
            rModel.setCustName(model.getCustName());
            rModel.setCustOldBal(model.getCustOldBal());
            if(orderDetails != null && orderDetails.getOrderId() != 0){
                rModel.setOrderTValue(orderDetails.getOrderTValue());
                rModel.setOrderDate(orderDetails.getOrderDate());
                rModel.setOrderId(orderDetails.getOrderId());
                rModel.setProductList(generateROrderProductList(productModelList, orderDetails.getProductList(), model.getProductPriceList()));
            } else {
                rModel.setProductList(generateNewROrderProductList(productModelList, model.getProductPriceList()));
            }
            rModel.setIsRegularFlag(1);
//            rModel.setProductList(productModelList);
            rOrderResponseList.add(rModel);
        }
        return rOrderResponseList;
    }

    @Override
    @Transactional
    public String handleSingleCustROrder(RegularOrderRequestModel model) {
        if(model.getOrderId() == 0){
            OrderMasterEntity oEntity = new OrderMasterEntity();
            oEntity.setOrderDate(model.getOrderDate());
            oEntity.setCustId(model.getCustId());
            oEntity.setIsRegularOrder(model.getIsRegularFlag());
            oEntity.setOrderTotalValue(model.getOrderTValue());
            int orderId = orderMasterRepo.save(oEntity).getOrderId();
            for(ProductModelForROrder oModel : model.getProductList()){
                if(oModel.getProductQuantity() > 0){
                    OrderLinesEntity olEntity = new OrderLinesEntity();
                    olEntity.setOrderId(orderId);
                    olEntity.setProductId(oModel.getProductId());
                    olEntity.setQuantity(oModel.getProductQuantity());
                    olEntity.setProductUnitPrice(oModel.getProductValue());
                    olEntity.setOrderValue(oModel.getOrderValue());
                    orderLinesRepo.save(olEntity);
                }
            }
            customerBalanceDetailsRepo.updateCustBalance(model.getCustId(), model.getCustOldBal());
        } else {
            for(ProductModelForROrder oModel : model.getProductList()) {
                if(oModel.getOrderLineId() != 0){
                    orderLinesRepo.updateOrderLineDetails(oModel.getOrderLineId(), oModel.getProductId(),
                            oModel.getProductQuantity(), oModel.getOrderValue());
                } else if (oModel.getProductQuantity() != 0) {
                    OrderLinesEntity entity = new OrderLinesEntity();
                    entity.setOrderId(model.getOrderId());
                    entity.setOrderValue(oModel.getOrderValue());
                    entity.setQuantity(oModel.getProductQuantity());
                    entity.setProductId(oModel.getProductId());
                    entity.setProductUnitPrice(oModel.getProductValue());
                    orderLinesRepo.save(entity);
                }
            }
            orderMasterRepo.updateOrderTValue(model.getOrderId(), model.getOrderTValue());
            customerBalanceDetailsRepo.updateCustBalance(model.getCustId(), model.getCustOldBal());
        }
        return "Order Saved Successfully for "+ model.getCustName() +" total balance :" +model.getCustOldBal();
    }

    @Override
    public String handleMultiCustROrder(MultiROrderRequestModel model) {
        return null;
    }

    @Override
    public List<String> rOrderTableCol() {
//        List<String> colHeaders = new ArrayList<>();
//        colHeaders.add("custName");
//        colHeaders.addAll(productDisplayDetailsRepo.getROrderTableName());
//        colHeaders.add("orderTValue");
//        colHeaders.add("saveButton");
        return productDisplayDetailsRepo.getROrderTableName();
    }

    List<ProductModelForROrder> generateNewROrderProductList(List<ProductModelForROrder> productModelList, List<PPriceModel> custPPriceList){
        List<ProductModelForROrder> modelResponse = new ArrayList<>();
        for (ProductModelForROrder model : productModelList) {
            ProductModelForROrder pModel = new ProductModelForROrder();
            pModel.setProductId(model.getProductId());
            pModel.setProductCode(model.getProductCode());
            BigDecimal productCustValue = getCustProductValue(custPPriceList, model.getProductId());
            if(productCustValue != null){
                pModel.setProductValue(productCustValue);
            } else {
                pModel.setProductValue(model.getProductValue());
            }
            modelResponse.add(pModel);
        }
        return modelResponse;
    }

    List<ProductModelForROrder> generateROrderProductList(List<ProductModelForROrder> productModelList, List<ProductModelForROrder> orderProductList, List<PPriceModel> custPPriceList){
        List<ProductModelForROrder> modelResponse = new ArrayList<>();
        for (ProductModelForROrder model : productModelList){
            ProductModelForROrder pModel = new ProductModelForROrder();
            ProductModelForROrder orderLineModel = getObjectFromOrderLine(orderProductList, model.getProductId());
            if(orderLineModel != null && orderLineModel.getProductId() != 0){
                pModel.setOrderValue(orderLineModel.getOrderValue());
                pModel.setOrderLineId(orderLineModel.getOrderLineId());
                pModel.setProductValue(orderLineModel.getProductValue());
                pModel.setProductQuantity(orderLineModel.getProductQuantity());
                pModel.setProductId(model.getProductId());
                pModel.setProductCode(model.getProductCode());
                modelResponse.add(pModel);
            } else {
                pModel.setProductId(model.getProductId());
                pModel.setProductCode(model.getProductCode());
                BigDecimal productCustValue = getCustProductValue(custPPriceList, model.getProductId());
                if(productCustValue != null){
                    pModel.setProductValue(productCustValue);
                } else {
                    pModel.setProductValue(model.getProductValue());
                }
                modelResponse.add(pModel);
            }

        }
        return modelResponse;
    }

    BigDecimal getCustProductValue(List<PPriceModel> modelList, int productId){
        for (PPriceModel model : modelList){
            if(model.getProductId() == productId) {
                return model.getProductValue();
            }
        }
        return null;
    }
    ProductModelForROrder getObjectFromOrderLine(List<ProductModelForROrder> orderProductList, int productId) {
        for (ProductModelForROrder model : orderProductList){
            if(model.getProductId() == productId){
                return model;
            }
        }
        return null;
    }
    RegularOrderResponseModel getCustROrderDetails(LocalDateTime orderDate, int custId){
        RegularOrderResponseModel model;
        try {
            model =mapper.readValue(orderLinesRepo.getCustROrderDetailsByDate(orderDate.toLocalDate(), custId).toString(), RegularOrderResponseModel.class);
            if(model.getOrderId() == 0){
                return null;
            }
            return model;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    List<CustDetailsAndPPriceListModel> getCustProductPriceList(int routeId){
        List<CustDetailsAndPPriceListModel> modelList = new ArrayList<>();
        List<Object> dbResponse = customerProductPriceRepo.getCustProductPriceList(routeId);
        for(Object obj : dbResponse){
            CustDetailsAndPPriceListModel model;
            try {
                model = mapper.readValue(obj.toString(), CustDetailsAndPPriceListModel.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            modelList.add(model);
        }
        return modelList;
    }
    List<ProductModelForROrder> getProductListWithBasePrice(){
        List<Object> dbPResponse = productRatesRepo.generateBaseProductsOrderList();
        List<ProductModelForROrder> baseProductList = new ArrayList<>();
        for(Object obj : dbPResponse){
            ProductModelForROrder model;
            try {
                model = mapper.readValue(obj.toString(), ProductModelForROrder.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            baseProductList.add(model);
        }
        return baseProductList;
    }

}
