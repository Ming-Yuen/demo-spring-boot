package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "product",
        uniqueConstraints  = {
                @UniqueConstraint(name = "uk_product", columnNames = {"productId"})
        }
)
public class Product extends BaseEntity {
    @Column(nullable = false, insertable = true, updatable = false)
    private String productId;
    @Column(nullable = false)
    private String productName;
    private String category;
    private Integer enable;

    @Data
    public static class ProductId{
        String productId;
    }
}
