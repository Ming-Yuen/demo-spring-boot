package com.demo.schedule.mapper;

import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.schedule.entity.BatchTask;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);
    BatchTask scheduleRequestConvertToSchedule(ScheduleUpdateRequest scheduleUpdateRequest);
}
