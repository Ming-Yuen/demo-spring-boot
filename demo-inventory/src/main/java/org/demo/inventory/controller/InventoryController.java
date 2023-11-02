package org.demo.inventory.controller;

import com.demo.common.controller.ControllerPath;
import org.demo.inventory.dto.InventoryAdjustmentRequest;
import org.demo.inventory.dto.InventoryAdjustmentResponse;
import org.demo.inventory.service.InventoryService;
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
