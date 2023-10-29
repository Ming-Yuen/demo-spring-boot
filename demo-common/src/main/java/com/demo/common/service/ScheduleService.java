package com.demo.common.service;

import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.common.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    void update(ScheduleUpdateRequest scheduleUpdateRequest);

    List<Schedule> getAllSchedule();

    Schedule findByName(String scheduleName);
}
