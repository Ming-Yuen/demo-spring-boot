package com.demo.admin.dao;

import com.demo.admin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, String>{
    User findByUserName(String username);
    List<User> findByUserNameIn(String... userNameList);
}
