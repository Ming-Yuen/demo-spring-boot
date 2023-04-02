package com.demo.admin.dao;

import com.demo.admin.eneity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<User, Long> {
    User findByUserNameAndPassword(String username, String password);

    List<User> findByUserName(String username);
}
