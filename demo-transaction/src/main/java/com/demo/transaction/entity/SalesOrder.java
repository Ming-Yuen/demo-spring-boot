package com.demo.transaction.entity;

import com.demo.common.entity.BaseEntity;
import com.demo.transaction.entity.enums.PaymentEnum;
import com.demo.transaction.entity.enums.SalesStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class SalesOrder extends BaseEntity {
    private String orderId;
    private OffsetDateTime txDatetime;
    private String storeCode;
    private String customerName;
    private String salesPerson;
    private BigDecimal totalAmount;
    private BigDecimal discount;

    @Enumerated(EnumType.ORDINAL)
    private PaymentEnum paymentMethod;

    @Enumerated(EnumType.ORDINAL)
    private SalesStatusEnum status;
    private String priority;
    private String shippingMethod;
    private String shippingAddress;
    private String orderSource;
    private String orderType;
    private String remark;
}
