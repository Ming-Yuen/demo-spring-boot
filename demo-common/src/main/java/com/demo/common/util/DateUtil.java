package com.demo.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static OffsetDateTime convertOffsetDatetime(String value, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime localDateTime = LocalDateTime.parse(value, formatter);
        return OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
    }

    public static OffsetDateTime convertOffsetDatetime(Date date) {
        Instant instant = date.toInstant();
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);
        return offsetDateTime;
    }

    public static OffsetDateTime convertOffsetDatetime(LocalDate localDate){
        return localDate.atStartOfDay().atOffset(ZoneOffset.UTC);
    }

}
