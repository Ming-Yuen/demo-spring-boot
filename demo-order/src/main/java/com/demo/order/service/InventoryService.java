package com.demo.order.service;

import com.demo.common.dto.InventoryAdjustmentRequest;
import com.demo.common.dto.InventoryUpdateRequest;
import com.demo.common.vo.InventoryAdjustmentResponse;
import com.demo.order.entity.Inventory;

import java.util.List;

public interface InventoryService {
    InventoryAdjustmentResponse adjustmentRequest(List<InventoryAdjustmentRequest> request);
    void adjustment(List<Inventory> request);

    void insert(List<Inventory> request);

    InventoryAdjustmentResponse updateRequest(List<InventoryUpdateRequest> request);
}
