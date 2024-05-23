package com.milkman.service;


import com.milkman.model.NewProductModel;
import com.milkman.model.UpdateProductModel;
import com.milkman.model.product.ProductModelForOrder;

import java.util.List;

public interface ProductDetailsMasterService {

	String addNewProduct(NewProductModel newModel);
	String updateProductDetails(UpdateProductModel updateModel);
	String deleteProductDetails(int productId);
	List<ProductModelForOrder> getActiveProductList();
}
