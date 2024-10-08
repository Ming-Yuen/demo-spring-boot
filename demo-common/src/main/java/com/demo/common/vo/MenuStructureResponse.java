package com.demo.common.vo;

import com.demo.common.dto.ApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuStructureResponse extends ApiResponse {
    private List<MenuTree> menu = new LinkedList<MenuTree>();
    @Data
    public static class MenuTree{
        private String id;
        private String parent;
        private String icon;
        private String name;
        private String type;
        private String link;
        private List<MenuTree> child;
    }
}
