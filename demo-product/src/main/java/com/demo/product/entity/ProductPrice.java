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

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints  = {
                @UniqueConstraint(name = "uk_productPrice", columnNames = {"region","sku"})
        }
)
public class ProductPrice extends BaseEntity {
    @Column(nullable = false, insertable = true, updatable = false)
    private String region;
    @Column(nullable = false, insertable = true, updatable = false)
    private String productId;
    private BigDecimal price;
    private LocalDate effectiveDate;
    private String currencyUnit;
}
