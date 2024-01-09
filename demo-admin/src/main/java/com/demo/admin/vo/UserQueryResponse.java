package com.demo.admin.vo;

import com.demo.admin.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class UserQueryResponse {
    private List<UserInfo> userInfoList;
}
