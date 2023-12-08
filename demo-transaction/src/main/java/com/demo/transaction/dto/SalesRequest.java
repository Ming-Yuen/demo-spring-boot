package com.demo.transaction.dto;

import com.demo.transaction.entity.enums.PaymentEnum;
import com.demo.transaction.entity.enums.SalesStatusEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class SalesRequest {
    private String orderId;
    private OffsetDateTime txDatetime;
    private String storeCode;
    private String region;
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
//    @Delegate
    private List<SalesItem> salesItems;
    @Data
    public static class SalesItem{
        private String productId;
        private BigDecimal amount;
        private int qty;

    }
}
