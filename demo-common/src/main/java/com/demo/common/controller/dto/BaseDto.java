package com.demo.common.controller.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
public class BaseDto {
    private String creator;
    private OffsetDateTime tx_creation_time;
    private OffsetDateTime tx_modification_time;
    private String modifier;
}
