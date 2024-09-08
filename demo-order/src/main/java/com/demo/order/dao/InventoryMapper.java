package com.demo.order.dao;

import com.demo.order.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;
@Mapper
public interface InventoryMapper {

    void insert(@Param("inv") Inventory inventories);

    void updateAdjustment(@Param("inv") Inventory inventories);
    Set<String> findByProductIds(@Param("inventories") List<Inventory> inventories);
}
