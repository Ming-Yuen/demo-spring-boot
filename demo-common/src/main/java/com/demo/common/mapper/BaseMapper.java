package com.demo.common.mapper;

import com.demo.common.entity.BaseEntity;

import java.util.List;

public interface BaseMapper<E extends BaseEntity> {
    void insert(List<E> list);
}
