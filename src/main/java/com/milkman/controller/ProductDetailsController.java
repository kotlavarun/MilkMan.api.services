package com.milkman.controller;

import com.milkman.model.NewProductModel;
import com.milkman.model.UpdateProductMRPModel;
import com.milkman.model.UpdateProductModel;
import com.milkman.model.product.ProductModelForOrder;
import com.milkman.service.ProductDetailsMasterService;
import com.milkman.service.ProductRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductDetailsController {
	@Autowired
	ProductDetailsMasterService productDetailsMasterServcie;

	@Autowired
	ProductRateService productRateService;

	@RequestMapping(value = "/newProduct", method = RequestMethod.POST)
	public ResponseEntity<?> addNewProduct(@RequestBody NewProductModel newModel) {
		String response = productDetailsMasterServcie.addNewProduct(newModel);
		if(!response.isEmpty()){
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.badRequest().body("Please contact admin");
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateProductDetails(@RequestBody UpdateProductModel updateModel){
		String response = productDetailsMasterServcie.updateProductDetails(updateModel);
		if(!response.isEmpty()){
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.badRequest().body("Please contact admin");
	}

	@RequestMapping(value = "/delete/{productId}", method = RequestMethod.POST)
	public ResponseEntity<?> deleteProduct(@PathVariable int productId){
		String response = productDetailsMasterServcie.deleteProductDetails(productId);
		if(!response.isEmpty()){
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.badRequest().body("Please contact admin76");
	}

	@RequestMapping(value = "/updateRates", method = RequestMethod.POST)
	public ResponseEntity<?> updateProductMRPDetails(@RequestBody UpdateProductMRPModel model){
		String response = productRateService.updateProductMRPDetails(model);
		if(!response.isEmpty()){
			return ResponseEntity.ok().body(response);
		}
		return ResponseEntity.badRequest().body("Please contact admin");
	}

	@RequestMapping(value = "/productList", method = RequestMethod.GET)
	public ResponseEntity<?> getActiveProductListForOrder()	{
		List<ProductModelForOrder> response = productDetailsMasterServcie.getActiveProductList();
		return ResponseEntity.ok().body(response);
	}

}
