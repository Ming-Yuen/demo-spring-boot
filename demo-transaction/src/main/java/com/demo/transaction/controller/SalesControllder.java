package com.demo.transaction.controller;

import com.demo.common.controller.ControllerPath;
import com.demo.common.dto.SalesRequest;
import com.demo.transaction.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ControllerPath.sales)
public class SalesControllder {
    @Autowired
    private SalesService salesService;
    @PostMapping(path = ControllerPath.create, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@Valid @RequestBody List<SalesRequest> request){
        salesService.updateSalesRequest(request);
    }
}
