package com.milkman.service;

import java.util.List;

import com.milkman.entity.CustomerDetailsMasterEntity;
import com.milkman.model.*;

public interface CustomerDetailsMasterService {
	
	List<CustomerDetailsMasterEntity> getAllDetails();
	String addNewCustomerDetails(NewCustDetailsModel model);
	String editCustomerDetails(EditCustInfoDetails model);
	String deActivateCustomer(int custId);
	List<CustomerSearchModel> getCustSearchList();

}
