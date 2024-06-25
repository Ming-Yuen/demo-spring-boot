package com.demo.schedule.service;

import com.demo.schedule.dto.BatchTaskInvokeRequest;
import com.demo.schedule.dto.BatchTaskUpdateRequest;
import com.demo.schedule.entity.BatchTask;

import java.util.List;

public interface BatchTaskService {
    void update(BatchTaskUpdateRequest batchTaskUpdateRequest);

    BatchTask findByName(String batchTaskName);

    List<BatchTask> getAllBatchTask();

    void invoke(BatchTaskInvokeRequest batchTaskInvokeRequest);
}
