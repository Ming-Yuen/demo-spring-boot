package com.demo.common.dto;

import com.demo.common.enums.PaymentEnum;
import com.demo.common.enums.SalesStatusEnum;
import lombok.Data;

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

    private PaymentEnum paymentMethod;

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
