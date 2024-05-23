package com.milkman.repo;

import com.milkman.entity.CustomerBalanceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerBalanceDetailsRepo extends JpaRepository<CustomerBalanceDetails, Integer> {
    @Modifying
    @Query(value = "update customer_balance_details set balance = :bal where cust_id = :custId ;", nativeQuery = true)
    void updateCustBalance(@Param("custId") int custId, @Param("bal") BigDecimal balance);
}
