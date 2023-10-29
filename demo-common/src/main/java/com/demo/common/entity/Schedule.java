package com.demo.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "schedule",
        indexes = {
                @Index(name = "idx_schedule", columnList = "name,enable")
        }
)
public class Schedule extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String jobClass;
    @Column(nullable = false)
    private String cron;
    @Column(nullable = false)
    private Integer enable;
    private String config;
}
