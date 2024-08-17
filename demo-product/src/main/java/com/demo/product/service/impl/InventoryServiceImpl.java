package com.demo.product.service.impl;

import com.demo.product.dao.InventoryMapper;
import com.demo.product.dto.InventoryAdjustmentRequest;
import com.demo.product.dto.InventoryUpdateRequest;
import com.demo.product.entity.Inventory;
import com.demo.product.mapper.InventoryMapping;
import com.demo.product.service.InventoryService;
import com.demo.product.vo.InventoryAdjustmentResponse;
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
