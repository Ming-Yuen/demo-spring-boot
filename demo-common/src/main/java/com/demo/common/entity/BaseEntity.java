package com.demo.common.entity;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
@MappedSuperclass
public class BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private String creator;

    @Column(nullable = false, updatable = false)
    private Timestamp creation_time = new Timestamp(System.currentTimeMillis());

    @Column(nullable = false)
    private Timestamp modification_time = new Timestamp(System.currentTimeMillis());

    @NotBlank
    @Column(nullable = false)
    private String modifier;
}