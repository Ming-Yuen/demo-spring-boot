package com.demo.transaction.service;

import com.demo.common.dto.SalesRequest;
import com.demo.transaction.entity.SalesOrder;

import java.util.List;

public interface SalesService {
    void updateSalesRequest(List<SalesRequest> request);

    void updateSales(List<SalesOrder> salesOrder);
}
