package org.demo.order.Dao;

import org.demo.order.entity.SalesOrder;
import org.springframework.data.repository.CrudRepository;

public interface SalesDao extends CrudRepository<SalesOrder, Long> {
    boolean existsByOrderId(String orderId);
}
