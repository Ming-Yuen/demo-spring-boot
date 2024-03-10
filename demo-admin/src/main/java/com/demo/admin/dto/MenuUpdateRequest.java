package com.demo.admin.dto;

import com.demo.admin.entity.enums.MenuType;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
