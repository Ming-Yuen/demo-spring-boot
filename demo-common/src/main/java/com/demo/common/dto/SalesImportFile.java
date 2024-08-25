package com.demo.common.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalesImportFile {
    private String orderId;
    private String txDatetime;
    private String storeCode;
    private String customerName;
    private String salesPerson;
    private String productId;
    private BigDecimal amount;
    private int qty;
    private BigDecimal unitPrice;
//    private String discountMethod;
//    private BigDecimal discount;
}
