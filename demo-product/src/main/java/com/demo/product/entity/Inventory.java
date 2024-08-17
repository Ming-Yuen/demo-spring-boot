package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class Inventory extends BaseEntity {
    private String productId;
    private Integer qty;
}
