package com.demo.common.dto;

import jakarta.validation.constraints.NotNull;

public class PageRequest {
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
}
