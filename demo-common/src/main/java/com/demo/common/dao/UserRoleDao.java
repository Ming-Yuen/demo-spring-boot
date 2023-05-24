package com.demo.common.dao;

import com.demo.common.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface UserRoleDao extends CrudRepository<UserRole, Long> {

    Collection<UserRole> findByRoleLevelGreaterThanEqual(Integer role_Level);

    UserRole findByUid(Long i);
}

