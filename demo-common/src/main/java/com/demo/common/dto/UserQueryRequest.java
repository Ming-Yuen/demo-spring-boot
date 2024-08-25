package com.demo.common.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserQueryRequest {
    private String[] userNameList;
}
