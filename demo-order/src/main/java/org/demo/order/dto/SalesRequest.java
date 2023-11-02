package org.demo.order.dto;

import lombok.Data;
import lombok.experimental.Delegate;
import org.demo.order.entity.enums.PaymentEnum;
import org.demo.order.entity.enums.SalesStatusEnum;

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
        private String sku;
        private BigDecimal amount;
        private int qty;

    }
}