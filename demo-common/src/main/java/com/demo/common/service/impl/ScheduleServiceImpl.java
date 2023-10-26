package com.demo.common.service.impl;

import com.demo.common.dao.ScheduleDao;
import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.common.entity.Schedule;
import com.demo.common.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleDao scheduleDao;
    @Override
    public void update(ScheduleUpdateRequest scheduleUpdateRequest) {

    }
    @Override
    public List<Schedule> getAllSchedule(){
        return scheduleDao.findByEnable(1);
    }
}
