package com.demo.product.dao;

import com.demo.product.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;
@Mapper
public interface ProductMapper {
    List<Product> findByEnable(int enable);
    boolean existsByProductId(String productId);
    @MapKey("product_id")
    Set<String> findByProductIdIn(@Param("productIds") List<Product> productIds);
    void insert(@Param("products") List<Product> productList);
    void updateProducts(@Param("products") List<Product> products);
}
