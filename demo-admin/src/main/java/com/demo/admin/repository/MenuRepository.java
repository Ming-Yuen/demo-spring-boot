package com.demo.admin.repository;

import com.demo.admin.entity.MenuStructure;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface MenuRepository extends CrudRepository<MenuStructure, Long> {
    List<MenuStructure> findByRoleIdIn(Collection<Long> properties);

    long deleteByType(String web);
}
