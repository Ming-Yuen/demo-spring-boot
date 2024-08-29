package com.demo.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private final LoggingItemReadListener<?> loggingItemReadListener;

    public JobCompletionNotificationListener(LoggingItemReadListener<?> loggingItemReadListener) {
        this.loggingItemReadListener = loggingItemReadListener;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("beforeJob {}, status : {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("afterJob {}, status : {}", jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
    }
}