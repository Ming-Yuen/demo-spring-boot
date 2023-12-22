package com.demo.admin.dao;

import com.demo.common.dao.HibernateRepository;
import com.demo.admin.entity.UserInfo;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInfoDao extends CrudRepository<UserInfo, String>,QuerydslPredicateExecutor<UserInfo>, HibernateRepository<UserInfo> {//
    UserInfo findByUserName(String username);
    List<UserInfo> findByUserNameIn(String... userNameList);
}
