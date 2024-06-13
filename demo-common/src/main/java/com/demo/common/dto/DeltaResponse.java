package com.demo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeltaResponse extends ApiResponse {
    private ConcurrentLinkedQueue<String> success = new ConcurrentLinkedQueue<String>();
    private ConcurrentLinkedQueue<ErrorRecord> failedList = new ConcurrentLinkedQueue<ErrorRecord>();
    @Data
    @AllArgsConstructor
    public static class ErrorRecord{
        private Map<String, String> id = new HashMap<String, String>();
        private String errorMessage;

    }
}
