package com.demo.schedule.service;

import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.schedule.entity.BatchTask;

import java.util.List;

public interface BatchTaskService {
    void update(ScheduleUpdateRequest scheduleUpdateRequest);

    BatchTask findByName(String batchTaskName);

    List<BatchTask> getAllBatchTask();
}
