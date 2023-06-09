package com.demo.admin.dto;

import com.demo.common.controller.dto.DefaultResponse;
import lombok.Data;

import java.util.List;

@Data
public class UserRegisterResponse extends DefaultResponse {

    private List<String> username;

    public UserRegisterResponse(List<String> username) {
        this.username = username;
    }
}
