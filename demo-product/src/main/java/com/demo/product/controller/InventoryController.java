package com.demo.product.controller;

import com.demo.common.controller.ControllerPath;
import com.demo.product.dto.InventoryAdjustmentRequest;
import com.demo.product.dto.InventoryUpdateRequest;
import com.demo.product.service.InventoryService;
import com.demo.product.vo.InventoryAdjustmentResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ControllerPath.inventory)
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @PostMapping(path = ControllerPath.adjustment, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InventoryAdjustmentResponse adjustment(@Valid @RequestBody List<InventoryAdjustmentRequest> request){
        return inventoryService.adjustmentRequest(request);
    }

    @PostMapping(path = ControllerPath.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InventoryAdjustmentResponse update(@Valid @RequestBody List<InventoryUpdateRequest> request){
        return inventoryService.updateRequest(request);
    }
}
