package com.demo.schedule.service.impl;

import com.demo.schedule.dao.ScheduleDao;
import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.schedule.mapper.ScheduleMapper;
import com.demo.schedule.entity.Schedule;
import com.demo.schedule.service.ScheduleService;
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
