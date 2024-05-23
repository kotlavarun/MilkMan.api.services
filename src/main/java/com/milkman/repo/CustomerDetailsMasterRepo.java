package com.milkman.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.milkman.entity.CustomerDetailsMasterEntity;

import java.util.List;

@Repository
public interface CustomerDetailsMasterRepo extends JpaRepository<CustomerDetailsMasterEntity, Integer> {
    @Query(value = "Select cust_id, cust_name, cust_phone, cust_shop_name, cust_phone, route_id, emp_id from customer_details_master where cust_id = :custId ;", nativeQuery = true)
    CustomerDetailsMasterEntity getCustDetailsById(@Param("custId") int custId);
    @Query(value = "update customer_details_master set cust_name = :custName , cust_shop_name = :custShopName , cust_phone = :custPhone , route_id = :routeId, emp_id = :empId where cust_id = :custId ;", nativeQuery = true)
    int updateCustDetails(@Param("custId") int custId, @Param("custName") String custName, @Param("custShopName") String custShopName, @Param("custPhone") String custPhone, @Param("routeId") int routeId, @Param("empId") int empId);
    @Query(value = "update customer_details_master set is_active = 0 where cust_id = :custId ;", nativeQuery = true)
    int deActivate(@Param("custId") int custId);
    @Query(value = "select cust_id, cust_name, cust_phone, cust_shop_name, route_id, emp_id from customer_details_master;" , nativeQuery = true)
    List<Object[]> getCustomerDetailsForOrder();
    @Query(value = "select cust_id, cust_name, cust_phone from customer_details_master where is_active = 1 ;" , nativeQuery = true)
    List<Object[]> listForCustSearchField();
    @Query(value = "Select cust_id from customer_details_master where route_id = :routeId ;", nativeQuery = true)
    List<Integer> getListCustDetailsByRouteId(@Param("routeId") int routeId);
}
