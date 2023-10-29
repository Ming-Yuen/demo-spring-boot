package com.demo.common;

import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.common.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    ScheduleMapper INSTANCE = Mappers.getMapper(ScheduleMapper.class);
    Schedule scheduleRequestConvertToSchedule(ScheduleUpdateRequest scheduleUpdateRequest);
}
