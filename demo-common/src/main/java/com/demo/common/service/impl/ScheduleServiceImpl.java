package com.demo.common.service.impl;

import com.demo.common.ScheduleMapper;
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
    @Autowired
    private ScheduleMapper scheduleMapper;
    @Override
    public void update(ScheduleUpdateRequest scheduleUpdateRequest) {
        Schedule schedule = scheduleMapper.scheduleRequestConvertToSchedule(scheduleUpdateRequest);
        scheduleDao.save(schedule);
    }
    @Override
    public List<Schedule> getAllSchedule(){
        return scheduleDao.findByEnable(1);
    }

    @Override
    public Schedule findByName(String scheduleName) {
        return scheduleDao.findByName(scheduleName);
    }
}
