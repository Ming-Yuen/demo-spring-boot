package com.demo.admin.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserQueryRequest {
    private List<String> userNameList;
}
