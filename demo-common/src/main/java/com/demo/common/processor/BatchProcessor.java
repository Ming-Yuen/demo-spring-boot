package com.demo.common.processor;

import com.demo.common.config.AppConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
@Component
@AllArgsConstructor
public class BatchProcessor {

    private final AppConfig appConfig;

    public <T> void processInBatch(List<T> list, Integer size, Consumer<List<T>> batchConsumer) {
        int batchSize = size != null && size > 0 ? size : appConfig.getBatchSize();
        if (list == null || batchConsumer == null) {
            throw new IllegalArgumentException("Invalid arguments for batch processing");
        }

        for (int start = 0; start < list.size(); start += batchSize) {
            int end = Math.min(list.size(), start + batchSize);
            List<T> batch = list.subList(start, end);
            batchConsumer.accept(batch);
        }
    }
}
