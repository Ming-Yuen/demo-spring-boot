package com.demo.schedule.dto;

import lombok.Data;

@Data
public class BatchTaskUpdateRequest {
    private String name;
    private String description;
    private String jobClass;
    private String cron;
    private Integer enable;
    private String config;
}
