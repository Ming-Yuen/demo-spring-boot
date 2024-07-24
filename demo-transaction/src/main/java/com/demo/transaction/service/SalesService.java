package com.demo.transaction.service;

import com.demo.transaction.dto.SalesRequest;
import com.demo.transaction.entity.SalesOrder;

import java.util.List;

public interface SalesService {
    void updateSalesRequest(List<SalesRequest> request);

    void updateSales(SalesOrder... salesOrder);
}
