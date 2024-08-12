package com.demo.product.mapper;

import com.demo.product.dto.InventoryAdjustmentRequest;
import com.demo.product.dto.InventoryUpdateRequest;
import com.demo.product.entity.Inventory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapping {
    List<Inventory> adjustmentConvert(List<InventoryAdjustmentRequest> request);

    List<Inventory> inventoryConvert(List<InventoryUpdateRequest> request);
}
