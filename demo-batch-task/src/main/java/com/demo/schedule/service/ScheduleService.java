package com.demo.schedule.service;

import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    void update(ScheduleUpdateRequest scheduleUpdateRequest);

    Schedule findByName(String scheduleName);

    List<Schedule> getAllSchedule();
}
