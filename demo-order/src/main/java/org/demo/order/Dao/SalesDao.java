package org.demo.order.Dao;

import org.demo.order.eneity.Sales;
import org.springframework.data.repository.CrudRepository;

public interface SalesDao extends CrudRepository<Sales, Long> {
    Sales findByOrderId(String orderId);
}
