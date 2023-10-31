package com.demo.admin.dto;

import com.demo.common.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class UserQueryResponse {
    private List<UserInfo> userInfoList;
}
