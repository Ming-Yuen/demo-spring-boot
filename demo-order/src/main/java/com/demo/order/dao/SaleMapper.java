package com.demo.order.dao;

import com.demo.common.mapper.BaseMapper;
import com.demo.order.entity.SalesOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;
@Mapper
public interface SaleMapper extends BaseMapper<SalesOrder> {
    Set<String> findByOrderIdIn(@Param("salesOrders") List<SalesOrder> salesOrders);
}
