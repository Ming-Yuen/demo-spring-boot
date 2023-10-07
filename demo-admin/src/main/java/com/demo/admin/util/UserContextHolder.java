package com.demo.admin.util;

import com.demo.admin.entity.UserInfo;
import com.demo.admin.entity.UserInfo;

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
