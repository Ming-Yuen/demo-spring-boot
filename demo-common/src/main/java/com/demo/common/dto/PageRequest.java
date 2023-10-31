package com.demo.common.dto;

import javax.validation.constraints.NotNull;

public class PageRequest {
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
}
