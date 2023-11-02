package org.demo.order.controller;

import com.demo.common.controller.ControllerPath;
import com.demo.common.dto.DeltaResponse;
import org.demo.order.dto.SalesRequest;
import org.demo.order.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(ControllerPath.sales)
public class SalesControllder {
    @Autowired
    private SalesService salesService;
    @PostMapping(path = ControllerPath.create, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DeltaResponse create(@Valid @RequestBody List<SalesRequest> request){
        return salesService.create(request);
    }
}
