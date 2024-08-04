package com.demo.product.service.impl;

import com.demo.product.dao.InventoryRepository;
import com.demo.product.dto.InventoryAdjustmentRequest;
import com.demo.product.dto.InventoryUpdateRequest;
import com.demo.product.entity.Inventory;
import com.demo.product.mapper.InventoryMapper;
import com.demo.product.service.InventoryService;
import com.demo.product.vo.InventoryAdjustmentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Service
public class InventoryServiceImpl implements InventoryService {
    private InventoryRepository inventoryRepository;
    private InventoryMapper inventoryMapper;
    @Override
    public InventoryAdjustmentResponse adjustmentRequest(List<InventoryAdjustmentRequest> request) {
        adjustment(inventoryMapper.adjustmentConvert(request));
        return new InventoryAdjustmentResponse();
    }

    @Override
    public void adjustment(List<Inventory> request) {
        List<String> productIds = new ArrayList<>();
        List<Integer> qtyList = new ArrayList<>();
        inventoryRepository.updateAdjustment(productIds, qtyList);
    }

    @Override
    public InventoryAdjustmentResponse updateRequest(List<InventoryUpdateRequest> request) {
        update(inventoryMapper.inventoryConvert(request));
        return null;
    }

    @Override
    public void update(List<Inventory> request) {

    }
}
