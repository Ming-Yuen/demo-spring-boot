package com.demo.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.OffsetDateTime;


@Setter
@Getter
public class BaseEntity implements Serializable {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Integer version = 1;
    private String createdBy;
    @JsonIgnore
    private OffsetDateTime createdAt;
    @JsonIgnore
    private String updatedBy;
    @JsonIgnore
    private OffsetDateTime updatedAt;
}
