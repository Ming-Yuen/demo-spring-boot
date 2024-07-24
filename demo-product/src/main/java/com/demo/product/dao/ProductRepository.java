package com.demo.product.dao;

import com.demo.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByEnable(int enable, Pageable pageable);
    boolean existsByProductId(String productId);

    <T> List<T> findByProductIdIn(Class<T> type, String... productIds);
}
