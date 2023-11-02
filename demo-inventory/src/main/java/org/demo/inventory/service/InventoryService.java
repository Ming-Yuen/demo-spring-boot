package org.demo.inventory.service;

import org.demo.inventory.dto.InventoryAdjustmentRequest;
import org.demo.inventory.dto.InventoryAdjustmentResponse;

import java.util.List;

public interface InventoryService {
    InventoryAdjustmentResponse adjustment(List<InventoryAdjustmentRequest> request);
}
