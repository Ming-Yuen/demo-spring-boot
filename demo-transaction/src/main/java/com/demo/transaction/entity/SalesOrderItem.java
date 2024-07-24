package com.demo.transaction.entity;

import com.demo.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
public class SalesOrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private SalesOrder salesOrder;
    private OffsetDateTime txDatetime;
    private Integer itemSequence;
    private String productId;
    private BigDecimal amount;
    private Integer qty;
    private BigDecimal unitPrice;
    private String discountMethod;
    private BigDecimal discount;
}
