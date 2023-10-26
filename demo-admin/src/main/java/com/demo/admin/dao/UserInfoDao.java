package com.demo.admin.dao;

import com.demo.common.entity.UserInfo;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoDao extends CrudRepository<UserInfo, Long>, QuerydslPredicateExecutor<UserInfo> {
    UserInfo findByUserName(String username);
}
