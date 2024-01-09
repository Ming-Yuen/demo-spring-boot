package com.demo.admin.vo;

import com.demo.common.dto.DefaultResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserRegisterResponse extends DefaultResponse {

    private List<String> username;

    public UserRegisterResponse(List<String> username) {
        this.username = username;
    }
}
