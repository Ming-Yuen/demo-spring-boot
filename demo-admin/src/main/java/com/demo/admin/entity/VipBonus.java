package com.demo.admin.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
@EqualsAndHashCode(callSuper = true)
@Data
public class VipBonus extends BaseEntity {
    private OffsetDateTime txDatetime;
    private String vipCode;
    private Integer bonus;
}
