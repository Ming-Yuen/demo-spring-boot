package com.demo.schedule.service.impl;

import com.demo.schedule.dao.ScheduleDao;
import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.schedule.entity.BatchTask;
import com.demo.schedule.mapper.ScheduleMapper;
import com.demo.schedule.service.BatchTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchTaskServiceImpl implements BatchTaskService {
    @Autowired
    private ScheduleDao scheduleDao;
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Override
    public void update(ScheduleUpdateRequest scheduleUpdateRequest) {
        BatchTask batchTask = scheduleMapper.scheduleRequestConvertToSchedule(scheduleUpdateRequest);
        scheduleDao.save(batchTask);
    }
    @Override
    public List<BatchTask> getAllBatchTask(){
        return scheduleDao.findByEnable(1);
    }

    @Override
    public BatchTask findByName(String batchTaskName) {
        return scheduleDao.findByName(batchTaskName);
    }
}
