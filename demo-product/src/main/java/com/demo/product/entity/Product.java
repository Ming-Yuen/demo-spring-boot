package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Entity
@Setter
@Getter
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
    private String company;
    private String category;
    private String riskRating;
    private Integer enable;

    public record ProductId(String productId){}
}
