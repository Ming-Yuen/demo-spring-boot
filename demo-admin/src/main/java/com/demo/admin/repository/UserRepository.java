package com.demo.admin.repository;

import com.demo.admin.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserInfo, String>{
    <T> List<T> findByUserNameIn(Class<T> type, String... username);
}
