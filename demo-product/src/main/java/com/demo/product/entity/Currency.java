package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@EqualsAndHashCode(callSuper = true)
@Entity
@Setter
@Getter
@Table(uniqueConstraints  = {
        @UniqueConstraint(name = "uk_currency", columnNames = {"unit"})
}
)
public class Currency extends BaseEntity {
    private String unit;
    private String rate;
}
