package com.demo.common.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TxHeaderRequest {
    private String region;
    private LocalDateTime txDatetime;
}
