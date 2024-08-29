package com.demo.common.controller;

import com.demo.common.dto.BatchTaskInvokeRequest;
import com.demo.common.dto.BatchTaskUpdateRequest;
import com.demo.common.dto.ApiResponse;
import com.demo.common.service.BatchTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(ControllerPath.BATCH_TASK)
public class BatchTaskController {
    @Autowired
    private BatchTaskService batchTaskService;
    @PostMapping(path = ControllerPath.UPDATE)
    public void update(BatchTaskUpdateRequest batchTaskUpdateRequest){
        batchTaskService.update(batchTaskUpdateRequest);
    }
    @PostMapping(path = ControllerPath.INVOKE)
    public ApiResponse invoke(@RequestBody @Validated BatchTaskInvokeRequest batchTaskInvokeRequest){
        batchTaskService.invoke(batchTaskInvokeRequest);
        return new ApiResponse();
    }
}
