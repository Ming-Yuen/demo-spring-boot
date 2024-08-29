package com.demo.order.entity;

import com.demo.common.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
public class Product extends BaseEntity {
    private String productId;
    private String productName;
    private String company;
    private String category;
    private String riskRating;
    private Integer enable;
}
