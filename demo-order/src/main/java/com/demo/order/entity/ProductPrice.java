package com.demo.order.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductPrice extends BaseEntity {
    private String productId;
    private BigDecimal price;
    private OffsetDateTime effectiveDate;
    private OffsetDateTime expiryDate;
}
