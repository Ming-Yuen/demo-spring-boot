package com.demo.admin.dao;

import com.demo.admin.entity.MenuStructure;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface MenuMapper {
    @Delete("DELETE FROM MenuStructure WHERE type = #{type}")
    long deleteByType(@Param("type") String type);
    @Select("SELECT * FROM MenuStructure WHERE privilege <= #{privilege} ")
    List<MenuStructure> findByPrivilege(@Param("privilege") Integer privilege);

    void insert(@Param("menuStructureList") List<MenuStructure> menuStructureList);
}
