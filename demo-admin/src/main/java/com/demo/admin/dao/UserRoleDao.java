package com.demo.admin.dao;

import com.demo.admin.entity.UserRole;
import com.demo.common.entity.enums.RoleLevelEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRoleDao extends JpaRepository<UserRole, Long> {

    Collection<UserRole> findByRoleLevelGreaterThanEqual(RoleLevelEnum role_Level);
}

