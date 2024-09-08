package com.demo.order.dao;

import com.demo.common.mapper.BaseMapper;
import com.demo.order.entity.SalesOrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SaleItemMapper extends BaseMapper<SalesOrderItem> {
}
