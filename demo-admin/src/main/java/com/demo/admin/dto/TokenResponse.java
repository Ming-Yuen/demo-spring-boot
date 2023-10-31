package com.demo.admin.dto;

import com.demo.common.dto.DefaultResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class TokenResponse extends DefaultResponse {
    private String token;
}
