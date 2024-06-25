package com.demo.schedule.service.impl;

import com.demo.schedule.dao.BatchTaskDao;
import com.demo.schedule.dto.BatchTaskInvokeRequest;
import com.demo.schedule.dto.BatchTaskUpdateRequest;
import com.demo.schedule.entity.BatchTask;
import com.demo.schedule.mapper.BatchTaskMapper;
import com.demo.schedule.service.BatchTaskService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BatchTaskServiceImpl implements BatchTaskService {
    private BatchTaskDao batchTaskDao;
    private BatchTaskMapper batchTaskMapper;
    private JobLauncher jobLauncher;
    private Job job;
    @Override
    public void update(BatchTaskUpdateRequest batchTaskUpdateRequest) {
        BatchTask batchTask = batchTaskMapper.scheduleRequestConvertToSchedule(batchTaskUpdateRequest);
        batchTaskDao.save(batchTask);
    }
    @Override
    public List<BatchTask> getAllTask(){
        return batchTaskDao.findByEnable(1);
    }

    @Override
    public void invoke(BatchTaskInvokeRequest batchTaskInvokeRequest) {
        BatchTask batchTask = batchTaskDao.findByName(batchTaskInvokeRequest.getName());
        try {
            JobExecution jobExecution = jobLauncher.run(job, new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters());
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
    public BatchTask findByName(String scheduleName) {
        return batchTaskDao.findByName(scheduleName);
    }
}
