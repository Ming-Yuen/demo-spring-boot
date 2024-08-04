package com.demo.product.dao;

import com.demo.product.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Modifying
    @Query("update Inventory set qty = qty + :qty where productId in :productIds")
    void updateAdjustment(@Param("productIds") List<String> productIds, @Param("qty") List<Integer> qty);
}
