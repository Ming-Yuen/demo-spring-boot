package com.demo.product.controller;

import com.demo.common.controller.ControllerPath;
import com.demo.product.dto.InventoryAdjustmentRequest;
import com.demo.product.vo.InventoryAdjustmentResponse;
import com.demo.product.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ControllerPath.inventory)
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @PostMapping(path = ControllerPath.adjustment, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public InventoryAdjustmentResponse create(@Valid @RequestBody List<InventoryAdjustmentRequest> request){
        return inventoryService.adjustment(request);
    }
}
