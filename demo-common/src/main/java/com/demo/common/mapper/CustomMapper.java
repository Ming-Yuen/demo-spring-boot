package com.demo.common.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface CustomMapper {
    CustomMapper INSTANCE = Mappers.getMapper(CustomMapper.class);
    @Named("convertOffsetDatetime")
    default OffsetDateTime convertOffsetDatetime(LocalDate localDate) {
        return localDate.atStartOfDay().atOffset(ZoneOffset.UTC);
    }
}
