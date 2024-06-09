package com.demo.admin.repository;

import com.demo.common.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRoleRepository extends JpaRepository<com.demo.admin.entity.UserRole, Long> {

    Collection<com.demo.admin.entity.UserRole> findByRoleLevelGreaterThanEqual(UserRole role_Level);
}

