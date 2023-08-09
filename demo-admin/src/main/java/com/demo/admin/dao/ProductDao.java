package com.demo.admin.dao;

import com.demo.admin.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;

public interface ProductDao extends CrudRepository<Product, Long> {

    Page<Product> findByActiveDateBefore(Date date, Pageable pageable);
    @Query(value = "SELECT * FROM product WHERE product.active_date > :date", nativeQuery = true)
    Page<Product> findByActiveDateLessThen(@Param("date") LocalDate date, Pageable pageable);
}
