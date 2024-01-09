package com.demo.product.service;

import com.demo.product.dto.InventoryAdjustmentRequest;
import com.demo.product.vo.InventoryAdjustmentResponse;

import java.util.List;

public interface InventoryService {
    InventoryAdjustmentResponse adjustment(List<InventoryAdjustmentRequest> request);
}
