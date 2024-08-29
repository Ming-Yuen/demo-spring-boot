package com.demo.order.mapper;

import com.demo.common.dto.InventoryAdjustmentRequest;
import com.demo.common.dto.InventoryUpdateRequest;
import com.demo.order.entity.Inventory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapping {
    List<Inventory> adjustmentConvert(List<InventoryAdjustmentRequest> request);

    List<Inventory> inventoryConvert(List<InventoryUpdateRequest> request);
}
