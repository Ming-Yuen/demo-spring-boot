package com.demo.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@MappedSuperclass
public class BaseEntity extends AuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column(name = "version", nullable = false, updatable = false)
    private Integer version = 1;
}
