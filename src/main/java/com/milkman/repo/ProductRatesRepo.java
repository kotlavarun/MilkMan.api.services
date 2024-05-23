package com.milkman.repo;


import com.milkman.entity.ProductRatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRatesRepo extends JpaRepository<ProductRatesEntity, Integer> {
    @Modifying
    @Query(value = "update product_rates set is_active = 0 where product_id = :productId ;", nativeQuery = true)
    void deActivateProductMRPRecord(@Param("productId") int productId);

    /* Generate product list with base price
    */
    @Query(value = "select JSON_OBJECT(\t'productId', pm.product_id,'productCode', pm.product_code,'productValue',  pr.product_mrp, " +
            "'productQuantity', 0.00, 'orderValue', 0.00) as json_data from product_details_master pm " +
            "join product_rates pr on pr_id = pm.product_id where pm.is_active = 1 and pr.is_active = 1 ;",nativeQuery = true)
    List<Object> generateBaseProductsOrderList();
}
