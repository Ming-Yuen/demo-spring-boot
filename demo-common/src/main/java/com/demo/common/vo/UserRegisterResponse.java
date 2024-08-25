package com.demo.common.vo;

import com.demo.common.dto.ApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserRegisterResponse extends ApiResponse {

    private List<String> username;

    public UserRegisterResponse(List<String> username) {
        this.username = username;
    }
}
