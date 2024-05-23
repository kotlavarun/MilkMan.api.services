package com.milkman.repo;

import com.milkman.entity.TransactionMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionMasterRepo extends JpaRepository<TransactionMasterEntity, Integer> {
    @Query(value = """
                 SELECT json_object("custId", cm.cust_id,"custName", cm.cust_name,"todayOrderTValue", ifnull(om.order_total_value, 0),
                 "balance", cbd.balance,"totalReceivedAmount",0,"transactionList", json_arrayagg(
                 json_object("empId", tm.emp_id,"empName", em.emp_name,"receivedAmount", ifnull(tm.received_amount, 0),"remark", tm.remark,
                 "createdOn",  DATE_FORMAT(tm.created_on, '%Y-%m-%d %H:%i:%s')))) as JsonData FROM customer_details_master cm
                 LEFT JOIN orders_master om ON cm.cust_id = om.cust_id AND DATE(om.order_date) = :date
                 LEFT JOIN customer_balance_details cbd on cm.cust_id = cbd.cust_id
                 LEFT JOIN transaction_master tm on cm.cust_id = tm.cust_id AND DATE(tm.created_on) = :date
                 LEFT JOIN employee_details_master em on tm.emp_id = em.emp_id
                 LEFT JOIN customer_display_order cdo on cm.cust_id = cdo.cust_id
                 WHERE cm.emp_id = :empId group by cm.cust_id, cm.cust_name order by cdo.display_order ;
            """, nativeQuery = true)
    List<Object> generateTransListBasedOnEmpIdAndDate(@Param("empId") int empId, @Param("date") LocalDate date);
}
