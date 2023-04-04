package com.demo.security.dao;

import com.demo.security.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {

    User findByUserId(String adminId);
}
