package com.demo.product.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryAdjustmentRequest {
    private String productId;
    private int qty;
}
