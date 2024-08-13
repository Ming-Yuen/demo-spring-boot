package com.demo.batckTask.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingItemReadListener<T> implements ItemReadListener<T> {
    private int count = 0;

    @Override
    public void beforeRead() {
        // No-op
    }

    @Override
    public void afterRead(T item) {
        count++;
        if (count % 500 == 0) {
            log.info("Processed {} items", count);
        }
    }

    @Override
    public void onReadError(Exception ex) {
        log.error("Error reading item", ex);
    }
}