package com.demo.admin.dto;

import com.demo.admin.entity.MenuStructure;
import com.demo.common.controller.dto.DefaultResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class MenuStructureResponse extends DefaultResponse {
    private List<MenuTree> menu = new LinkedList<MenuTree>();
    @Data
    public static class MenuTree{
        private MenuStructure current;
        private List<MenuTree> child = new LinkedList<MenuTree>();

        public MenuTree(MenuStructure menu){
            this.current = menu;
        }
    }
}
