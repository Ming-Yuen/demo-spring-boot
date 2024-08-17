package com.demo.transaction.dao;

import com.demo.transaction.entity.SalesOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;
@Mapper
public interface SaleMapper {
    Set<String> findByOrderIdIn(@Param("orderIds") String... orderIds);

    void insert(@Param("salesOrders") List<SalesOrder> salesOrders);
}
