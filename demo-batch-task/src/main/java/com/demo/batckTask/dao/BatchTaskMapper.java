package com.demo.batckTask.dao;

import com.demo.batckTask.entity.BatchTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface BatchTaskMapper {
    @Select("select * from batchTask where enable = 1")
    List<BatchTask> findByEnable(@Param("num") String enable);
    @Select("select * from batchTask where taskName = #{taskName}")
    BatchTask findByName(String taskName);

    void insert(@Param("tasks") BatchTask batchTask);
    void updateTask(@Param("tasks") BatchTask batchTask);
}
