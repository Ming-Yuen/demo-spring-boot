package com.demo.product.service.impl;

import com.demo.product.dto.InventoryAdjustmentRequest;
import com.demo.product.dto.InventoryAdjustmentResponse;
import com.demo.product.service.InventoryService;
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
