package com.demo.common.util;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneRules;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DateUtil {
    private static final Map<String, DateTimeFormatter> formatterCache = new ConcurrentHashMap<>();
    private static final ZoneRules ZoneRules = ZoneOffset.systemDefault().getRules();
    public static OffsetDateTime convertOffsetDatetime(String format, String dateTimeString){
        DateTimeFormatter formatter = formatterCache.computeIfAbsent(format, DateTimeFormatter::ofPattern);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
        ZoneOffset defaultOffset = ZoneRules.getOffset(localDateTime);
        return OffsetDateTime.of(localDateTime, defaultOffset);
    }
}
