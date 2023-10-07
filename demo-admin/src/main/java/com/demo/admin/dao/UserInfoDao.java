package com.demo.admin.dao;

import com.demo.admin.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoDao extends CrudRepository<UserInfo, Long> {
    UserInfo findByUserName(String username);
}
