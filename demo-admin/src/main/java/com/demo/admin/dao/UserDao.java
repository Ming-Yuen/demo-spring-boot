package com.demo.admin.dao;

import com.demo.admin.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<UserInfo, String>{
    UserInfo findByUserName(String username);
    List<UserInfo> findByUserNameIn(String... userNameList);
}
