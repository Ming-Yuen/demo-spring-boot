package com.demo.security.dao;

import com.demo.security.model.User;

public interface UserDao {
    public User findByUsername(String username);
}
