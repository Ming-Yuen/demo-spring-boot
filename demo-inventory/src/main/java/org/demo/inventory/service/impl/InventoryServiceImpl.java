package org.demo.inventory.service.impl;

import org.demo.inventory.dto.InventoryAdjustmentRequest;
import org.demo.inventory.dto.InventoryAdjustmentResponse;
import org.demo.inventory.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Override
    public InventoryAdjustmentResponse adjustment(List<InventoryAdjustmentRequest> request) {
        request.parallelStream().forEach(item->{

        });
        return null;
    }
}
