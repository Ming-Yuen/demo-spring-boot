package com.demo.common.dao;

import com.demo.common.entity.Schedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleDao extends CrudRepository<Schedule, Long> {
    List<Schedule> findByEnable(int i);

    Schedule findByName(String scheduleName);
}
