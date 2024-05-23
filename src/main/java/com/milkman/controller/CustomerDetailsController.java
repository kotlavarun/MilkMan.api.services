package com.milkman.controller;

import com.milkman.model.CustomerPriceForProduct;
import com.milkman.model.EditCustInfoDetails;
import com.milkman.model.NewCustDetailsModel;
import com.milkman.service.CustomerDetailsMasterService;
import com.milkman.service.CustomerProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerDetailsController {
	@Autowired
	CustomerDetailsMasterService customerDetailsMasterService;
	@Autowired
	CustomerProductPriceService customerProductPriceService;

	@RequestMapping(value = "/getAllCustomers", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCustomerDetails() {

		return ResponseEntity.ok().body(customerDetailsMasterService.getAllDetails()) ;
	}

	@RequestMapping(value = "/listCustForSearchField", method = RequestMethod.GET)
	public ResponseEntity<?> listCustForSearchField() {
		return ResponseEntity.ok().body(customerDetailsMasterService.getCustSearchList()) ;
	}

	@RequestMapping(value = "/addNewCust", method = RequestMethod.POST)
	public ResponseEntity<?> addNewCustomers(@RequestBody NewCustDetailsModel model){
		String response = customerDetailsMasterService.addNewCustomerDetails(model);
		if(!response.isEmpty()){
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.badRequest().body("Please contact admin new customer could not be added");
	}

	@RequestMapping(value = "/updateCust", method = RequestMethod.POST)
	public ResponseEntity<?> updateCustDetails(@RequestBody EditCustInfoDetails model){
		String response = customerDetailsMasterService.editCustomerDetails(model);
		if(!response.isEmpty()){
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.badRequest().body("Please contact admin, customer details could not be edited");
	}

	@RequestMapping(value = "/deactivate/{custId}", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateCustDetails(@PathVariable int custId){
		String response = customerDetailsMasterService.deActivateCustomer(custId);
		if(!response.isEmpty()){
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.badRequest().body("Please contact admin, can not deactivate customer");
	}

	@RequestMapping(value = "/cpPriceList/{custId}", method = RequestMethod.GET)
	public ResponseEntity<?> getProductsPriceListForCustomer(@PathVariable int custId){
		List<CustomerPriceForProduct> response = customerProductPriceService.getProductPListByCustId(custId);
		if(response!= null){
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.badRequest().body("Please contact admin, service can not be found");
	}

	@RequestMapping(value = "/editCustProductPrice", method = RequestMethod.POST)
	public ResponseEntity<?> editCustomerProductPrice(@RequestBody CustomerPriceForProduct model){
		String response = customerProductPriceService.updatedCustomerNewProductPrice(model);
		if(!response.isEmpty()){
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.badRequest().body("Please contact admin, can not deactivate customer");
	}
}
