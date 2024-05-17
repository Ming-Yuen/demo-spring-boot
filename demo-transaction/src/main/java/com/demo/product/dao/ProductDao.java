package com.demo.product.dao;

import com.demo.common.dao.HibernateRepository;
import com.demo.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;

public interface ProductDao extends CrudRepository<Product, Long>, QuerydslPredicateExecutor<Product>, HibernateRepository<Product> {
    Page<Product> findByActiveDateBefore(Date date, Pageable pageable);
    @Query(value = "SELECT * FROM product WHERE product.active_date > :date", nativeQuery = true)
    Page<Product> findByActiveDateLessThen(@Param("date") LocalDate date, Pageable pageable);
    boolean existsByProductId(String productId);
}
