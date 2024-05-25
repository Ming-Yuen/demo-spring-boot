package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ProductComposition extends BaseEntity {
    private String productId;
    private Integer percentage;
}
