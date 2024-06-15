package com.demo.admin.repository;

import com.demo.admin.entity.MenuStructure;
import com.demo.admin.enums.PrivilegeType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuRepository extends CrudRepository<MenuStructure, Long> {

    long deleteByType(String web);

    List<MenuStructure> findByPrivilegeIn(PrivilegeType... privilegeTypeList);
}
