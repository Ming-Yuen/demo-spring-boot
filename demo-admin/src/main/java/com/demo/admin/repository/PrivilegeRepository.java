package com.demo.admin.repository;

import com.demo.admin.entity.Privilege;
import com.demo.admin.enums.PrivilegeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    <T> List<T>  findByPrivilegeIn(Class<T> type, PrivilegeType... privilegeType);
}

