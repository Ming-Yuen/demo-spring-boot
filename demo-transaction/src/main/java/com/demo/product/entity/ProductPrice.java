package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_productPrice", columnNames = {"productId", "effectiveDate"})
})
public class ProductPrice extends BaseEntity {
    @Column(nullable = false, insertable = true, updatable = false)
    private String productId;
    private BigDecimal price;
    private OffsetDateTime effectiveDate;
    private OffsetDateTime expiryDate;
    private String currencyUnit;
    private String description;
    @Data
    public static class ProductPriceId{
        String productId;
    }
}
