package com.demo.batckTask.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("{}, status : {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("{}, status : {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
    }
}