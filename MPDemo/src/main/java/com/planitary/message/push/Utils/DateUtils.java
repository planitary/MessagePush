package com.planitary.message.push.Utils;


import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期计算工具类
 */
public class DateUtils {
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = new ThreadLocal<>();
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * date转LocalDateTime
     */
    public static LocalDateTime dateConvert2LocalDateTime(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 获取simpleDateFormat的实体
     */

    public static SimpleDateFormat getSimpleDateFormat(){
        SimpleDateFormat simpleDateFormat = THREAD_LOCAL.get();
        if (simpleDateFormat == null){
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            THREAD_LOCAL.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }

    /**
     * 时间差计算
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param key           标记位，以天数或小时位单位返回差值,默认返回毫秒
     * @return
     */
    public static long getTimeDiff(LocalDateTime startTime,LocalDateTime endTime,String key){
        if (key.equals("days")){
            return Duration.between(startTime,endTime).toDays();
        }
        if (key.equals("hours")){
            return Duration.between(startTime,endTime).toHours();
        }
        return Duration.between(startTime,endTime).toMillis();
    }




}