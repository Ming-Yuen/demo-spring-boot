package com.demo.batckTask.entity;

import com.demo.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Entity
@Setter
@Getter
public class BatchTask extends BaseEntity {
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