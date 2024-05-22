package com.demo.product.dao;

import com.demo.common.dao.HibernateRepository;
import com.demo.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface ProductDao extends CrudRepository<Product, Long>, HibernateRepository<Product> {
    Page<Product> findByEnable(int enable, Pageable pageable);
    boolean existsByProductId(String productId);

    <T> List<T> findByProductId(String[] productIds, Class<T> type);
}
