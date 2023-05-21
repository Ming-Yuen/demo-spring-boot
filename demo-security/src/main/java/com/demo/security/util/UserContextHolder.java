package com.demo.security.util;

import com.demo.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserContextHolder {
    private static final ThreadLocal<User> contextHolder = new ThreadLocal<>();

    public static void setUser(User user) {
        contextHolder.set(user);
    }
    public static User getUser() {
        return contextHolder.get();
    }
    public static void clear() {
        contextHolder.remove();
    }
}
