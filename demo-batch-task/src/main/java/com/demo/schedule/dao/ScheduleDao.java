package com.demo.schedule.dao;

import com.demo.schedule.entity.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ScheduleDao extends CrudRepository<Schedule, Long> {
    List<Schedule> findByEnable(int i);

    Schedule findByName(String scheduleName);
}
