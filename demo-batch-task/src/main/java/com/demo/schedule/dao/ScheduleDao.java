package com.demo.schedule.dao;

import com.demo.schedule.entity.BatchTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ScheduleDao extends CrudRepository<BatchTask, Long> {
    List<BatchTask> findByEnable(int i);

    BatchTask findByName(String scheduleName);
}
