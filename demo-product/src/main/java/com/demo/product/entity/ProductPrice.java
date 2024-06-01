package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ProductPrice extends BaseEntity {
    @Column(nullable = false, insertable = true, updatable = false)
    private String productId;
    private BigDecimal price;
    private OffsetDateTime effectiveDate;
    private OffsetDateTime expiryDate;
    @Data
    public static class ProductPriceId{
        String productId;
    }
}
