package com.demo.schedule.controller;

import com.demo.common.controller.ControllerPath;
import com.demo.schedule.dto.BatchTaskInvokeRequest;
import com.demo.schedule.dto.BatchTaskUpdateRequest;
import com.demo.schedule.service.BatchTaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerPath.BATCH_TASK)
@AllArgsConstructor
public class BatchTaskController {
    private BatchTaskService batchTaskService;
    @PostMapping(path = ControllerPath.UPDATE)
    public void update(BatchTaskUpdateRequest batchTaskUpdateRequest){
        batchTaskService.update(batchTaskUpdateRequest);
    }

    @PostMapping(path = ControllerPath.INVOKE)
    public void invoke(BatchTaskInvokeRequest batchTaskInvokeRequest){
        batchTaskService.invoke(batchTaskInvokeRequest);
    }
}
