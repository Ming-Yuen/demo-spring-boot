package com.demo.admin.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

public class FirstJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 执行定时任务的逻辑
        System.out.println("定时任务执行：" + LocalDateTime.now());
    }
}