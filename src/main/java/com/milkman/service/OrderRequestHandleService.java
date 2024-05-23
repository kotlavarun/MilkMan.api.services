package com.milkman.service;

import com.milkman.model.orders.*;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRequestHandleService {
    String singleOrderRequestHandler(OrderRequestModel requestModel);
    List<RegularOrderResponseModel> getRegularOrderByDate(LocalDateTime date, int routeId);
    String handleSingleCustROrder(RegularOrderRequestModel model);
    String handleMultiCustROrder(MultiROrderRequestModel model);
    List<String> rOrderTableCol();
}
