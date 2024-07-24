package com.demo.transaction.dao;

import com.demo.transaction.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface SalesRepository extends JpaRepository<SalesOrder, Long> {
    Set<SalesOrder.OrderId> findByOrderIdIn(String... orderId);
}
