package com.demo.batckTask.service.impl;

import com.demo.batckTask.dao.BatchTaskDao;
import com.demo.batckTask.dto.BatchTaskInvokeRequest;
import com.demo.batckTask.dto.BatchTaskUpdateRequest;
import com.demo.batckTask.entity.BatchTask;
import com.demo.batckTask.mapper.BatchTaskMapper;
import com.demo.batckTask.service.BatchTaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class BatchTaskServiceImpl implements BatchTaskService {
    private BatchTaskDao batchTaskDao;
    private BatchTaskMapper scheduleMapper;
    private JobLauncher jobLauncher;
    private final ApplicationContext applicationContext;
    private JobExplorer jobExplorer;
    @Override
    public void update(BatchTaskUpdateRequest batchTaskUpdateRequest) {
        BatchTask batchTask = scheduleMapper.batchTaskRequestConvertToRequest(batchTaskUpdateRequest);
        batchTaskDao.save(batchTask);
    }
    @Override
    public List<BatchTask> getAllTask(){
        return batchTaskDao.findByEnable(1);
    }


    @Async(AsyncExecutionAspectSupport.DEFAULT_TASK_EXECUTOR_BEAN_NAME)
    @Override
    public void invoke(BatchTaskInvokeRequest batchTaskInvokeRequest) {
        String jobName = batchTaskInvokeRequest.getTaskName();
        try {
            Job jobToRun = (Job) applicationContext.getBean(jobName);

            JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
                    .getNextJobParameters(jobToRun)
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(jobToRun, jobParameters);

            log.info("Job {} started with status: {}", jobName, jobExecution.getStatus());
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        } catch (JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BatchTask findByName(String batchTaskName) {
        return batchTaskDao.findByName(batchTaskName);
    }
}
