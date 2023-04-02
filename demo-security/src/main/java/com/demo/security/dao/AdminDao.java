package com.demo.security.dao;

import com.demo.security.entity.Admin;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminDao extends CrudRepository<Admin, Long> {

    Admin findByAdminId(String adminId);
}
