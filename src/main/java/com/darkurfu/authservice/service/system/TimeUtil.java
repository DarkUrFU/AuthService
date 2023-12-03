package com.darkurfu.authservice.service.system;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class TimeUtil {

    public TimeUtil(){}

    static public Timestamp getCurrentTime(){
        return Timestamp.from(getCurrentTimeInstance());
    }

    static public Timestamp getCurrentTimePlusHours(long hours){
        return Timestamp.from(getCurrentTimeInstancePlusHours(hours));
    }

    static public Timestamp getCurrentTimePlusDays(long days){
        return Timestamp.from(getCurrentTimeInstancePlusDays(days));
    }

    static public Instant getCurrentTimeInstancePlusHours(long hours){
        return ZonedDateTime.now(ZoneId.of("Asia/Yekaterinburg")).plusHours(hours).toInstant();
    }

    static public Instant getCurrentTimeInstancePlusDays(long days){
        return ZonedDateTime.now(ZoneId.of("Asia/Yekaterinburg")).plusDays(days).toInstant();
    }

    static public Instant getCurrentTimeInstance(){
        return ZonedDateTime.now(ZoneId.of("Asia/Yekaterinburg")).toInstant();
    }
}
