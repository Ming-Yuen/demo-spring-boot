package com.demo.common.service;

import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.common.entity.Schedule;
import org.quartz.SchedulerException;

import java.util.List;

public interface ScheduleService {
    void update(ScheduleUpdateRequest scheduleUpdateRequest);

    Schedule findByName(String scheduleName);

    List<Schedule> getAllSchedule();
}
