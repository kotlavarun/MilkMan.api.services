package com.milkman.controller;

import com.milkman.model.orders.*;
import com.milkman.service.OrderRequestHandleService;
import com.milkman.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.milkman.util.CommonConversions.convertStringToDate;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderRequestHandleService orderRequestHandleService;

    @RequestMapping(value = "/saveSOrder", method = RequestMethod.POST)
    public ResponseEntity<?> newSingleOrderRequest(@RequestBody OrderRequestModel requestModel) {
        Status status = new Status();
        if(!requestModel.getProductList().isEmpty()){
           String response = orderRequestHandleService.singleOrderRequestHandler(requestModel);
           if(!response.isEmpty()){
               status.setMessage(response);
               return ResponseEntity.ok().body(status);
           } else {
               status.setMessage("Sorry could not save the order");
               return ResponseEntity.badRequest().body(status);
           }
        } else {
            status.setMessage("Product List Empty could not place the order");
            return ResponseEntity.ok().body(status);
        }
    }

    /**
     * Method to handle regular Order
     */
    @RequestMapping(value = "/getRegularOrder", method = RequestMethod.POST)
    public ResponseEntity<?> getRegularOrderByDateAndRouteId(@RequestBody DateRouteIdModel model) {
        List<RegularOrderResponseModel> responseModelList = orderRequestHandleService.getRegularOrderByDate(model.getOrderDate(), model.getRouteId());
        return ResponseEntity.ok().body(responseModelList);
    }
    @RequestMapping(value = "/saveRegularOrder", method = RequestMethod.POST)
    public ResponseEntity<?> saveSingleCustROrder(@RequestBody RegularOrderRequestModel model) {
        String response = orderRequestHandleService.handleSingleCustROrder(model);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/saveMRegularOrder", method = RequestMethod.POST)
    public ResponseEntity<?> saveMultiCustROrder(@RequestBody MultiROrderRequestModel model) {
        String response = orderRequestHandleService.handleMultiCustROrder(model);
        return ResponseEntity.ok().body(response);
    }

    @RequestMapping(value = "/getROrderTableCol", method = RequestMethod.GET)
    public ResponseEntity<?> getROrderTableCol() {
        List<String> rOrderTableCol = orderRequestHandleService.rOrderTableCol();
        return ResponseEntity.ok().body(rOrderTableCol);
    }






}
