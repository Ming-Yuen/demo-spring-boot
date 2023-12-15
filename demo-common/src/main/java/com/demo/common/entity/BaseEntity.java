package com.demo.common.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class BaseEntity extends AuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Column(name = "version", nullable = false, updatable = false)
    private Integer version;
}
