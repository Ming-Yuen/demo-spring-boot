package com.demo.admin.controller;

import com.demo.admin.service.VipService;
import com.demo.common.controller.ControllerPath;
import com.demo.common.dto.VipBonusAdjustmentRequest;
import com.demo.common.util.ValidList;
import com.demo.common.vo.VipBonusAdjustmentResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerPath.VIP)
@AllArgsConstructor
public class VIPController {
    private VipService vipService;
    @PostMapping(path = ControllerPath.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public VipBonusAdjustmentResponse update(@RequestBody @Validated ValidList<VipBonusAdjustmentRequest> request){
        vipService.adjustmentBonusRequest(request);
        return new VipBonusAdjustmentResponse();
    }
}
