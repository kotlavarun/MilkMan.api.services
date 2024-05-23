package com.milkman.service;

import java.util.ArrayList;
import java.util.List;

import com.milkman.entity.CustomerProductPriceEntity;
import com.milkman.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milkman.entity.CustomerDetailsMasterEntity;
import com.milkman.repo.CustomerDetailsMasterRepo;

@Service
public class CustomerDetailsMasterServiceImpl implements CustomerDetailsMasterService {

	@Autowired
	CustomerDetailsMasterRepo repository;
	@Autowired
	CustomerProductPriceService customerProductPriceService;

	@Override
	public List<CustomerDetailsMasterEntity> getAllDetails() {
		return repository.findAll();
	}

	@Transactional
	@Override
	public String addNewCustomerDetails(NewCustDetailsModel model) {
		CustomerDetailsMasterEntity custEntity = new CustomerDetailsMasterEntity();
		custEntity.setCustName(model.getCustName());
		custEntity.setCustShopName(model.getCustShopName());
		custEntity.setCustPhone(model.getPhone());
		custEntity.setRouteId(model.getRouteId());
		custEntity.setEmpId(model.getEmpId());
		custEntity.setIsActive(1);
		custEntity = repository.save(custEntity);
		if(custEntity.getCustId() > 0){
			List<CustomerProductPriceEntity> list = new ArrayList<CustomerProductPriceEntity>();
			for (int i = 0; i < model.getPriceList().size(); i++){
				CustomerProductPriceEntity entity = new CustomerProductPriceEntity();
				entity.setCustId(custEntity.getCustId());
				entity.setProductId(model.getPriceList().get(i).getProductId());
				entity.setProductPrice(model.getPriceList().get(i).getPrice());
				entity.setIsActive(1);
				list.add(entity);
			}
			customerProductPriceService.addNewCustomerPrices(list);
			return "New "+custEntity.getCustName()+" customer has been added ";
		} else {
			return "Contact admin New Customer could not be saved";
		}
	}

	@Override
	public String editCustomerDetails(EditCustInfoDetails model) {
		int count = repository.updateCustDetails(model.getCustId(), model.getCustName(), model.getCustShopName(), model.getPhone(), model.getRouteId(), model.getEmpId());
		if(count == 1){
			return model.getCustName()+" Customer details updated";
		} else{
			return "Could not updated customer details";
		}
	}

	@Override
	public String deActivateCustomer(int custId) {
		int count = repository.deActivate(custId);
		if(count == 1){
			return "Customer have been deactivated";
		} else{
			return "Customer can not deactivated";
		}
	}

	@Override
	public List<CustomerSearchModel> getCustSearchList() {
		List<Object[]> dbResponse = repository.listForCustSearchField();
		List<CustomerSearchModel> modelList = new ArrayList<>();
		for(Object[] obj : dbResponse){
			CustomerSearchModel model = new CustomerSearchModel();
			model.setCustId((Integer) obj[0]);
			model.setCustName(String.valueOf(obj[1]));
			model.setPhone(String.valueOf(obj[2]));
			modelList.add(model);
		}
		return modelList;
	}

}