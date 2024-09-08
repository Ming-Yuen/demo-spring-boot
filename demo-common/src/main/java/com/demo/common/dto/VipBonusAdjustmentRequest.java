package com.demo.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class VipBonusAdjustmentRequest {
    private OffsetDateTime txDatetime;
    private String vipCode;
    private BigDecimal adjBonus;
}
