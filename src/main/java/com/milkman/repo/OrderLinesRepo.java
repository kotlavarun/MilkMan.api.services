package com.milkman.repo;

import com.milkman.entity.OrderLinesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderLinesRepo extends JpaRepository<OrderLinesEntity, Integer> {

    @Query(value = "SELECT o_line_id, order_id, product_id, quantity, product_unit_value, order_value FROM sales.order_lines;", nativeQuery = true)
    List<OrderLinesEntity> getOrderLineBaseOnOrderId(@Param("orderId") int orderId);

    /* Get Regular order details from order date & customer id
     */
    @Query(value = "SELECT json_object(\n" +
            "\t\"orderId\", om.order_id,\n" +
            "    \"orderDate\", DATE_FORMAT(om.order_date, '%Y-%m-%d %H:%i:%s'),\n" +
            "    \"custId\", om.cust_id,\n" +
            "    \"orderTValue\", om.order_total_value,\n" +
            "    \"isRegularOrder\", om.is_regular_order,\n" +
            "    \"productList\", JSON_ARRAYAGG(\n" +
            "\tJSON_OBJECT(\n" +
            "\t\t\"orderLineId\", ol.o_line_id,\n" +
            "\t\t\"productId\", ol.product_id,\n" +
            "        \"productQuantity\", ol.quantity,\n" +
            "        \"orderValue\", ol.order_value,\n" +
            "        \"productValue\", ol.product_unit_value\n" +
            "    ))\n" +
            ") as json_data FROM orders_master om\n" +
            "left join order_lines ol on om.order_id = ol.order_id\n" +
            "where date(om.order_date) = :orderDate and om.cust_id = :custId and om.is_regular_order = 1;", nativeQuery = true)
    Object getCustROrderDetailsByDate(@Param("orderDate") LocalDate orderDate, @Param("custId") int custId);

    @Modifying
    @Query(value = "update order_lines set quantity = :quantity, order_value = :orderValue where product_id = :productId and o_line_id = :oLineId ;" ,nativeQuery = true)
    void updateOrderLineDetails(@Param("oLineId") int oLineId, @Param("productId") int productId,
                                @Param("quantity") double quantity, @Param("orderValue") BigDecimal orderValue);
}
