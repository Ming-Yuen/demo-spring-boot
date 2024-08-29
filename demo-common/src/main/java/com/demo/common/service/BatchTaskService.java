package com.demo.common.service;

import com.demo.common.dto.BatchTaskInvokeRequest;
import com.demo.common.dto.BatchTaskUpdateRequest;
import com.demo.common.entity.BatchTask;

import java.util.List;

public interface BatchTaskService {
    void update(BatchTaskUpdateRequest batchTaskUpdateRequest);

    BatchTask findByName(String scheduleName);

    List<BatchTask> getAllTask();

    void invoke(BatchTaskInvokeRequest batchTaskInvokeRequest) ;
}
