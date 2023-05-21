package com.demo.common.dao;

import com.demo.common.entity.User;
import com.demo.common.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface UserRoleDao extends CrudRepository<UserRole, Long> {

    Collection<UserRole> findByLessThanRoleLevel(Integer role_Level);
}

