package com.demo.common.dto;

import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
public class MenuUpdateRequest {

    @NotNull
    private String menuType;
    @Valid
    @NotNull
    private List<Menu> menu;

    @Data
    public static class Menu{

        private String parent;
        @NotBlank
        private String title;
        @NotNull
        private String type;
        @NotBlank
        private String link;
        @NotNull
        private Long roleId;
    }
}
