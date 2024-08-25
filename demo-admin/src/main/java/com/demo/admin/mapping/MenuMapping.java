package com.demo.admin.mapping;

import com.demo.common.dto.MenuUpdateRequest;
import com.demo.admin.entity.MenuStructure;
import com.demo.common.vo.MenuStructureResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapping {

    List<MenuStructure> convert(List<MenuUpdateRequest.Menu> menu);

    MenuStructureResponse.MenuTree convert(MenuStructure menuItem);
}
