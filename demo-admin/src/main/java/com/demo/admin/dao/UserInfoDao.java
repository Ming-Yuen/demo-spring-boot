package com.demo.admin.dao;

import com.demo.common.entity.UserInfo;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInfoDao extends CrudRepository<UserInfo, Long>, QuerydslPredicateExecutor<UserInfo> {
    UserInfo findByUserName(String username);
    List<UserInfo> findByUserNameIn(List<String> userNameList);
}
