package com.demo.admin.vo;

import com.demo.admin.entity.MenuStructure;
import com.demo.common.dto.DefaultResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuStructureResponse extends DefaultResponse {
    private List<MenuTree> menu = new LinkedList<MenuTree>();
    @Data
    public static class MenuTree{
        private String parent;
        private String icon;
        private String title;
        private String type;
        private String link;
        private List<MenuTree> child;
    }
}
