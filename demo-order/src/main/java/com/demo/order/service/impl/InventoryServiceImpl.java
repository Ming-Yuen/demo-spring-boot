package com.demo.order.service.impl;

import com.demo.common.dto.InventoryAdjustmentRequest;
import com.demo.common.dto.InventoryUpdateRequest;
import com.demo.order.mapper.InventoryMapping;
import com.demo.order.dao.InventoryMapper;
import com.demo.order.entity.Inventory;
import com.demo.order.service.InventoryService;
import com.demo.common.vo.InventoryAdjustmentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class InventoryServiceImpl implements InventoryService {
    private InventoryMapper inventoryMapper;
    private InventoryMapping inventoryMapping;
    @Override
    public InventoryAdjustmentResponse adjustmentRequest(List<InventoryAdjustmentRequest> request) {
        adjustment(inventoryMapping.adjustmentConvert(request));
        return new InventoryAdjustmentResponse();
    }

    @Override
    public void adjustment(List<Inventory> inventories) {
        inventoryMapper.updateAdjustment(inventories);
    }

    @Override
    public InventoryAdjustmentResponse updateRequest(List<InventoryUpdateRequest> request) {
        adjustment(inventoryMapping.inventoryConvert(request));
        return new InventoryAdjustmentResponse();
    }

    @Override
    public void insert(List<Inventory> inventories) {
        Set<String> existingProducts = inventoryMapper.findByProductIds(inventories);
        inventories = inventories.stream().filter(existingProducts::contains).collect(Collectors.toList());
        if(!inventories.isEmpty()){
            inventoryMapper.insert(inventories);
        }
    }
}
