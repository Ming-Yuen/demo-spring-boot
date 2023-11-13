package com.demo.common.util;

import com.demo.common.dto.TxHeaderRequest;
import com.demo.common.entity.UserInfo;

import java.time.LocalDateTime;

public class ContextHolder {
    private static final ThreadLocal<UserInfo> userContextHolder = new ThreadLocal<>();
    public static void setUser(UserInfo user) {
        userContextHolder.set(user);
    }
    public static UserInfo getUser() {
        return userContextHolder.get();
    }
    public static void clear() {
        if(userContextHolder.get() != null) {
            userContextHolder.remove();
        }
    }
}
