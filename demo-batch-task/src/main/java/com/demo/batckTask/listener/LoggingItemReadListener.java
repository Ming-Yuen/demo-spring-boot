package com.demo.batckTask.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingItemReadListener<T> implements ItemReadListener<T>, StepExecutionListener {

    private int count = 0;
    private String stepName;

    @Override
    public void beforeRead() {
        // No-op
    }

    @Override
    public void afterRead(T item) {
        count++;
        if (count % 500 == 0) {
            log.info("Processed {} items in step {}", count, stepName);
        }
    }

    @Override
    public void onReadError(Exception ex) {
        log.error("Error reading item in step {}", stepName, ex);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepName = stepExecution.getStepName();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // No-op
        return null;
    }
}