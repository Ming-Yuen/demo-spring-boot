package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class Currency extends BaseEntity {
    private String unit;
    private String rate;
}
