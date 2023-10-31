package com.demo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ErrorRecord {
    private String id;
    private String errorMessage;
}
