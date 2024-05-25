package com.demo.admin.mapper;

import com.demo.admin.dto.MenuUpdateRequest;
import com.demo.admin.entity.MenuStructure;
import com.demo.admin.vo.MenuStructureResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    Iterable<MenuStructure> convert(List<MenuUpdateRequest.Menu> menu);

    MenuStructureResponse.MenuTree convert(MenuStructure menuItem);
}
