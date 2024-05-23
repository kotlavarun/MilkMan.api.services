package com.milkman.repo;

import com.milkman.entity.ProductDetailsMasterEntity;
import com.milkman.model.NewProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailsMasterRepo extends JpaRepository<ProductDetailsMasterEntity, Integer> {

    @Modifying
    @Query(value = "update product_details_master set product_code = :pCode, product_name = :pName, product_desc = :pDesc, box_quantity = :boxQ where product_id = :productId ;", nativeQuery = true)
    int updateProductDetails(@Param("productId") int productId, @Param("pCode") String productCode, @Param("pName") String productName, @Param("pDesc") String productDesc, @Param("boxQ") double boxQuantity);

    @Modifying
    @Query(value = "update product_details_master set is_active = 0 where product_id = :productId ;", nativeQuery = true)
    int deactivateProduct(@Param("productId") int productId);

    @Query(value = """
               select json_object(
               'productId', pm.product_id,
               'productName', pm.product_name,
               'productMRP', pr.product_mrp
            ) as JSON_DATA from product_details_master pm\s
            join product_rates pr on pr.product_id = pm.product_id\s
            where pr.is_active = 1 and pm.is_active = 1;""", nativeQuery = true)
    List<Object> getActiveProductList();

}
