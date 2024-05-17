package com.demo.admin.dao;

import com.demo.common.dao.HibernateRepository;
import com.demo.admin.entity.User;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User, String>,QuerydslPredicateExecutor<User>, HibernateRepository<User> {//
    User findByUserName(String username);
    List<User> findByUserNameIn(String... userNameList);
}
