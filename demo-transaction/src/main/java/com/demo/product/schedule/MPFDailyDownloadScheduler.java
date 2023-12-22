package com.demo.product.schedule;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;
@Slf4j
@Configuration
public class MPFDailyDownloadScheduler extends QuartzJobBean {
    @Autowired
    private Job mpfDailyDownloadJob;
    @Autowired
    private JobLauncher jobLauncher;
    @Override
    protected void executeInternal(JobExecutionContext context) {
        try {
            JobParameters jobParameters= new JobParametersBuilder().toJobParameters();
            JobExecution result = jobLauncher.run(mpfDailyDownloadJob, jobParameters);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
