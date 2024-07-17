package com.demo.common.mapper;

import com.demo.common.util.DateUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface CustomMapper {
    default OffsetDateTime fromOffsetDatetime(LocalDate localDate) {
        return localDate.atStartOfDay().atOffset(ZoneOffset.UTC);
    }

    default OffsetDateTime fromOffsetDatetime(Date date) {
        return DateUtil.convertOffsetDatetime(date);
    }
    @Named("yyyy/MM/dd HH:mm:ss.SSS")
    default OffsetDateTime fromOffsetDatetime1(String date) {
        return DateUtil.convertOffsetDatetime(date, "yyyy-MM-dd HH:mm:ss.SSS");
    }
}
