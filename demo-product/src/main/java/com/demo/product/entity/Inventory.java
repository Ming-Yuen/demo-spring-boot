package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Entity
@Setter
@Getter
public class Inventory extends BaseEntity {
    private String productId;
    private Integer qty;
}
