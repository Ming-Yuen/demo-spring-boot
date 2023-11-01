package org.demo.order.Dao;

import org.demo.order.entity.SalesOrderItem;
import org.springframework.data.repository.CrudRepository;

public interface SalesItemDao extends CrudRepository<SalesOrderItem, Long> {
}
