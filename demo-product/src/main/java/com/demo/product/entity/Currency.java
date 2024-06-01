package com.demo.product.entity;

import com.demo.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(uniqueConstraints  = {
        @UniqueConstraint(name = "uk_currency", columnNames = {"unit"})
}
)
public class Currency extends BaseEntity {
    private String unit;
    private String rate;
}
