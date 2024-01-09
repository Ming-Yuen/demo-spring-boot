package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "product",
        uniqueConstraints  = {
                @UniqueConstraint(name = "uk_product1", columnNames = {"productId","region"})
        }
)
public class Product extends BaseEntity implements Serializable {
    @Column(nullable = false, insertable = true, updatable = false)
    private String region;
    @Column(nullable = false, insertable = true, updatable = false)
    private String productId;
    @Column(nullable = false)
    private String name;
    private String category;
    private LocalDate activeDate;
}
