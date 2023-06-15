package com.demo.admin.dao;

import com.demo.admin.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ProductDao extends CrudRepository<Product, Long> {

    Page<Product> findByActiveDateBefore(Date date, Pageable pageable);
}
