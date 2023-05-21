package com.demo.admin.dao;

import com.demo.admin.entity.MenuStructure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface MenuDao extends CrudRepository<MenuStructure, Long> {
    MenuStructure findByRoleIn(Collection<String> properties);
}
