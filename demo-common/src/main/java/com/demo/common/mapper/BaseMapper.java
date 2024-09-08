package com.demo.common.mapper;

import com.demo.common.entity.BaseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<E extends BaseEntity> {
    void insert(@Param("param") E param);
}
