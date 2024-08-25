package com.demo.admin.dao;

import com.demo.admin.entity.Users;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {
    @Select("SELECT 1 FROM users WHERE user_name = #{username}")
    Integer existsByUsername(@Param("username") String username, RowBounds rowBounds);
    @Select("SELECT user_password FROM users WHERE user_name = #{username} limit 1")
    String findByFirstUserPassword(@Param("username") String username);

    Set<String> findByExistingUserName(@Param("users") List<Users> users, Page rowBounds);
    void insertUsers(@Param("users") List<Users> users);
    void updateUsers(@Param("users") List<Users> users);
    @Select("SELECT * FROM users WHERE user_name = #{username} limit 1")
    Users findByFirstUserName(@Param("username") String username);
    @Select("SELECT privilegeType FROM users WHERE user_name = #{username} limit 1")
    Integer findByPrivilegeType(@Param("username") String username);
}
