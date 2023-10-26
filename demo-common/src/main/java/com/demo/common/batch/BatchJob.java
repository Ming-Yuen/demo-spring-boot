package com.demo.common.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;

public class BatchJob {
    private String jobName;
    private JobLauncher jobLauncher;
    private JobLocator jobLocator;
    public void batchJob() throws Exception {
        Job job = jobLocator.getJob(jobName);
        JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
        jobLauncher.run(job, params);
    }
}
