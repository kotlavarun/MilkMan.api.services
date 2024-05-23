package com.milkman.repo;

import com.milkman.entity.ProductDisplayDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDisplayDetailsRepo extends JpaRepository<ProductDisplayDetails, Integer> {

    @Query(value = "select pd.product_code from product_details_master pd\n" +
            "left join product_display_details pdd on pd.product_id = pdd.product_id\n" +
            "where pd.is_active = 1 order by pdd.display_order;", nativeQuery = true)
    List<String> getROrderTableName();

}
