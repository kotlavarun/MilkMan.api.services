package com.milkman.repo;

import com.milkman.entity.CustomerProductPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerProductPriceRepo extends JpaRepository<CustomerProductPriceEntity, Integer> {
    @Modifying
    @Query(value = "update customer_product_price set is_active = 0 where product = :productId and cust_id = :custId ;", nativeQuery = true)
    void deactivateProductOldPrice(@Param("productId") int productId, @Param("custId") int custId);

    @Query(value = "insert into customer_product_price (cust_id, product_id, price) values (:custId, :productId, :price)", nativeQuery = true)
    CustomerProductPriceEntity saveNewRecord(@Param("productId") int productId, @Param("price") BigDecimal price, @Param("custId") int custId);

    @Query(value = "SELECT cust_id, product_id, price FROM sales.customer_product_price where cust_id = :custId and is_active = 1;", nativeQuery = true)
    List<Object[]> getProductPListByCustId(@Param("custId") int custId);

    /* Below query generates cust product price list
     */
    @Query(value = "Select json_object(\n" +
            "            \"custId\",cm.cust_id,\n" +
            "            \"custName\",cm.cust_name,\n" +
            "            \"custOldBal\", cbd.balance,\n" +
            "            \"productPriceList\", JSON_ARRAYAGG(\n" +
            "            JSON_OBJECT(\n" +
            "            \"productId\",cpp.product_id,\n" +
            "            \"productValue\",cpp.price\n" +
            "            ))\n" +
            "            ) as json_data\n" +
            "            from customer_details_master cm\n" +
            "            left join customer_product_price cpp on cm.cust_id = cpp.cust_id and cpp.is_active = 1\n" +
            "            left join customer_balance_details cbd on cm.cust_id = cbd.cust_id\n" +
            "            where cm.route_id = :routeId and cm.is_active = 1\n" +
            "            group by cm.cust_id, cm.cust_name;", nativeQuery = true)
    List<Object> getCustProductPriceList(@Param("routeId") int routeId);
}
