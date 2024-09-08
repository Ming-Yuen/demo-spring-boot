package com.demo.order.dao;

import com.demo.common.mapper.BaseMapper;
import com.demo.order.entity.Product;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Set;
@Mapper
public interface ProductMapper extends BaseMapper{
    List<Product> findByEnable(int enable, RowBounds rowBounds);
    boolean existsByProductId(String productId);
    @MapKey("product_id")
    Set<String> findByProductIdIn(@Param("products") List<Product> products);
    void updateProducts(@Param("product") Product products);
}
