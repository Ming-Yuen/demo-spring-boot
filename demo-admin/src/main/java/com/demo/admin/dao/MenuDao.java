package com.demo.admin.dao;

import com.demo.admin.entity.MenuStructure;
import com.demo.common.dao.HibernateRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface MenuDao extends CrudRepository<MenuStructure, Long> , HibernateRepository<MenuStructure> {
    List<MenuStructure> findByRoleIdIn(Collection<Long> properties);

    long deleteByType(String web);
}
