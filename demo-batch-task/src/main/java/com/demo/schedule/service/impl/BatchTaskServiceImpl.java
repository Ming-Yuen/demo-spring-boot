package com.demo.schedule.service.impl;

import com.demo.schedule.dao.BatchTaskDao;
import com.demo.schedule.dto.BatchTaskInvokeRequest;
import com.demo.schedule.dto.BatchTaskUpdateRequest;
import com.demo.schedule.entity.BatchTask;
import com.demo.schedule.mapper.BatchTaskMapper;
import com.demo.schedule.service.BatchTaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
    @Override
    public void update(BatchTaskUpdateRequest batchTaskUpdateRequest) {
        BatchTask batchTask = scheduleMapper.batchTaskRequestConvertToRequest(batchTaskUpdateRequest);
        batchTaskDao.save(batchTask);
    }
    @Override
    public List<BatchTask> getAllBatchTask(){
        return batchTaskDao.findByEnable(1);
    }

    @Override
    public void invoke(BatchTaskInvokeRequest batchTaskInvokeRequest) {
        String jobName = batchTaskInvokeRequest.getTaskName();
        try {
            Job jobToRun = (Job) applicationContext.getBean(jobName);

            JobExecution jobExecution = jobLauncher.run(jobToRun, new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters());

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
