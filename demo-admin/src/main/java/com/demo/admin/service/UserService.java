package com.demo.admin.service;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.common.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


public interface UserService {

    List<User> update(List<UserRegisterRequest> user);

    User findByUsername(String userId);

    @Cacheable()
    Collection<Long> getManageRoles(Long id);
}
