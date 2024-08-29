package com.demo.order.controller;

import com.demo.common.controller.ControllerPath;
import com.demo.common.dto.SalesRequest;
import com.demo.common.vo.InventoryAdjustmentResponse;
import com.demo.common.web.DemoRestTemplate;
import com.demo.order.service.SalesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping(ControllerPath.sales)
public class SalesController {
    @Value("${spring.cloud.consul.discovery.service-name}")
    private String INVENTORY_SRV_DOMAIN;
    private final SalesService salesService;
    private final DemoRestTemplate demoRestTemplate;
    public SalesController(SalesService salesService, DemoRestTemplate demoRestTemplate) {
        this.salesService = salesService;
        this.demoRestTemplate = demoRestTemplate;
    }
    @PostMapping(path = ControllerPath.create, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@Valid @RequestBody List<SalesRequest> request){
        salesService.updateSalesRequest(request);
        demoRestTemplate.postForEntity(INVENTORY_SRV_DOMAIN, salesService.convertInventoryRequest(request), InventoryAdjustmentResponse.class);
    }
}
