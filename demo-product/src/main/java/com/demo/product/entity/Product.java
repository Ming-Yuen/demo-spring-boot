package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "product",
        uniqueConstraints  = {
                @UniqueConstraint(name = "uk_product1", columnNames = "region,sku")
        }
)
public class Product extends BaseEntity {
    @Column(nullable = false, insertable = true, updatable = false)
    private String region;
    @Column(nullable = false, insertable = true, updatable = false)
    private String sku;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate activeDate;
}
