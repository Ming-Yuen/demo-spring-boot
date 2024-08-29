package com.demo.common.entity;

import com.demo.common.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class BatchTask extends BaseEntity {
    private String name;
    private String description;
    private String jobClass;
    private String cron;
    private Integer enable;
    private String config;
}