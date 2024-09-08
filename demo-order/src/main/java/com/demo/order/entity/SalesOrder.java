package com.demo.order.entity;

import com.demo.common.entity.BaseEntity;
import com.demo.common.enums.PaymentEnum;
import com.demo.common.enums.SalesStatusEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SalesOrder extends BaseEntity {
    private String orderId;
    private OffsetDateTime txDatetime;
    private String storeCode;
    private String customerName;
    private String salesPerson;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal discount = BigDecimal.ZERO;

    private PaymentEnum paymentMethod;

    private SalesStatusEnum status;
    private String priority;
    private String shippingMethod;
    private String shippingAddress;
    private String orderSource;
    private String orderType;
    private String remark;

    private List<SalesOrderItem> items = new ArrayList<>();
}
