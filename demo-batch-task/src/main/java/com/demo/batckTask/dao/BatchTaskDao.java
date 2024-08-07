package com.demo.batckTask.dao;

import com.demo.batckTask.entity.BatchTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BatchTaskDao extends CrudRepository<BatchTask, Long> {
    List<BatchTask> findByEnable(int i);

    BatchTask findByName(String scheduleName);
}
