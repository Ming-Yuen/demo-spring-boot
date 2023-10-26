package com.demo.common.util;

import com.demo.common.entity.UserInfo;

public class UserContextHolder {
    private static final ThreadLocal<UserInfo> contextHolder = new ThreadLocal<>();
    public static void setUser(UserInfo user) {
        contextHolder.set(user);
    }
    public static UserInfo getUser() {
        return contextHolder.get();
    }
    public static void clear() {
        contextHolder.remove();
    }
}
