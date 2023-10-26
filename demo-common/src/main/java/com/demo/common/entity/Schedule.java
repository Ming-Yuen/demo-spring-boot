package com.demo.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Schedule extends BaseEntity{
    private String name;
    private String jobClass;
    private String cron;
    private Integer enable;
}
