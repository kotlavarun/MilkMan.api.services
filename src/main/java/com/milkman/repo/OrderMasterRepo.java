package com.milkman.repo;

import com.milkman.entity.OrderMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface OrderMasterRepo extends JpaRepository<OrderMasterEntity, Integer> {
//    @Query(value = "select order_id, order_date, cust_id, order_total_value, remark, created_on  from sales.orders_master where date(order_date) = :requestedDate", nativeQuery = true)
//    List<OrderMasterEntity> getAllOrderOnRequestedDate(@Param("requestedDate") LocalDateTime requestedDate);
//    @Query(value = "select order_id, order_date, cust_id, order_total_value, remark, created_on  from sales.orders_master where date(order_date) = :orderDate and cust_id = :custId ;", nativeQuery = true)
//    List<OrderMasterEntity> getOrderListByDateAndCustId(@Param("orderDate") LocalDateTime orderDate, @Param("custId") int custId);
//
//    @Query(value = "select  json_object(\n" +
//            "'custId', cm.cust_id,\n" +
//            "'custName', cm.cust_name,\n" +
//            "'orderDate', DATE_FORMAT(om.order_date, '%Y-%m-%d %H:%i:%s'),\n" +
//            "'orderId', om.order_id,\n" +
//            "'orderTotalValue', om.order_total_value,\n" +
//            "'isRegularOrder', om.is_regular_order,\n" +
//            "'productList', JSON_ARRAYAGG(\n" +
//            "\tJSON_OBJECT(\n" +
//            "\t\t'productId', ol.product_id,\n" +
//            "        'productQuantity', ol.quantity,\n" +
//            "        'productUnitValue', ol.product_unit_value,\n" +
//            "        'orderValue', ol.order_value\n" +
//            "\t\t)\n" +
//            "    )\n" +
//            ") as JSON_DATA\n" +
//            "FROM customer_details_master cm \n" +
//            "left Join orders_master om on cm.cust_id = om.cust_id\n" +
//            "inner join order_lines ol on ol.order_id = om.order_id\n" +
//            "where  date(om.order_date)= :orderDate and cm.cust_id in :custIdsList \n" +
//            "GROUP BY cm.cust_id, cm.cust_name, om.order_id\n" +
//            "ORDER BY om.order_id ;\n", nativeQuery = true)
//    List<Object> getAllOrderBasedOnCustAndDate(@Param("orderDate") LocalDateTime orderDate, @Param("custIdsList") List<Integer> custIdsList);
        @Modifying
        @Query(value = "update orders_master set order_total_value = :orderTValue where order_id = :orderId ;", nativeQuery = true)
        void updateOrderTValue(@Param("orderId") int orderId, @Param("orderTValue") BigDecimal orderTValue);
}
