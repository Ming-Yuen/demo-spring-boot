package com.demo.transaction.dao;

import com.demo.transaction.entity.SalesOrderItem;
import org.springframework.data.repository.CrudRepository;

public interface SalesItemDao extends CrudRepository<SalesOrderItem, Long> {
}
