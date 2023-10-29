package com.demo.admin.dao;

import com.demo.admin.entity.UserInfoPending;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

public interface UsersPendingDao extends CrudRepository<UserInfoPending, Long> {
    UserInfoPending findByUserName(String username);
    @Procedure(name = "confirm_pending_user")
    void confirmPendingUser(String batchId);
}
