package com.demo.common.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class BaseDto {
    private String creator;
    private Date tx_creation_time;
    private Date tx_modification_time;
    private String modifier;
}
