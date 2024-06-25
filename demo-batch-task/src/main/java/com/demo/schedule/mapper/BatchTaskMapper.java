package com.demo.schedule.mapper;

import com.demo.schedule.dto.BatchTaskUpdateRequest;
import com.demo.schedule.entity.BatchTask;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BatchTaskMapper {
    BatchTaskMapper INSTANCE = Mappers.getMapper(BatchTaskMapper.class);
    BatchTask batchTaskRequestConvertToRequest(BatchTaskUpdateRequest batchTaskUpdateRequest);
}
