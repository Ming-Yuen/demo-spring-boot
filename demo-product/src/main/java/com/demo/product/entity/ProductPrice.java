package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    public record ProductPriceKeys(String productId, OffsetDateTime effectiveDate){}
    public record ProductCurrentPrice(String productId, OffsetDateTime effectiveDate, BigDecimal price){}
}
