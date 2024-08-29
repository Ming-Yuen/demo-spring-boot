package com.demo.common.dto;

import lombok.Data;

@Data
public class InventoryAdjustmentRequest {
    private String productId;
    private int adjQty;

}
