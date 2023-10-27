package com.demo.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Schedule extends BaseEntity{
    private String name;
    private String jobClass;
    private String cron;
    private Integer enable;
    private Map<String, Object> config;
}
