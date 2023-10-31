package org.demo.order.Dao;

import org.demo.order.eneity.SalesItem;
import org.springframework.data.repository.CrudRepository;

public interface SalesItemDao extends CrudRepository<SalesItem, Long> {
}
