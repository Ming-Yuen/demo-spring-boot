package com.demo.order.service;

import com.demo.common.dto.InventoryAdjustmentRequest;
import com.demo.common.dto.SalesRequest;
import com.demo.order.entity.SalesOrder;

import java.util.List;

public interface SalesService {
    void updateSalesRequest(List<SalesRequest> request);

    void updateSales(List<SalesOrder> salesOrder);

    List<InventoryAdjustmentRequest>  convertInventoryRequest(List<SalesRequest> request);
}
