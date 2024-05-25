package com.demo.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

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
