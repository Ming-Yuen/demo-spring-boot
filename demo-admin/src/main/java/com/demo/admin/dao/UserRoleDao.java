package com.demo.admin.dao;

import com.demo.common.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRoleDao extends JpaRepository<com.demo.admin.entity.UserRole, Long> {

    Collection<com.demo.admin.entity.UserRole> findByRoleLevelGreaterThanEqual(UserRole role_Level);
}

