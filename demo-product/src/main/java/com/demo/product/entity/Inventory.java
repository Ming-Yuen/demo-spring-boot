package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Inventory extends BaseEntity {
    private String productId;
    private Integer qty;
}
