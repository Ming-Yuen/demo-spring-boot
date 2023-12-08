package com.demo.transaction.dao;

import com.demo.transaction.entity.SalesOrder;
import org.springframework.data.repository.CrudRepository;

public interface SalesDao extends CrudRepository<SalesOrder, Long> {
    boolean existsByOrderId(String orderId);
}
