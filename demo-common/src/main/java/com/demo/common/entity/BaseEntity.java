package com.demo.common.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;


@Setter
@Getter
public class BaseEntity implements Serializable {

    private Long id;
    private Integer version = 1;
    private String createdBy;
    private OffsetDateTime createdAt;
    private String updatedBy;
    private OffsetDateTime updatedAt;
}
