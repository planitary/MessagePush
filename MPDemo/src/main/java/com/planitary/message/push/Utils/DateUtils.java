package com.planitary.message.push.Utils;


import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期计算工具类
 */
public class DateUtils {
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = new ThreadLocal<>();
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy年MM月dd日");

    /**
     * date转LocalDateTime
     */
    public static LocalDate dateConvert2LocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
     * @param type           标记位，以天数或月份为单位返回差值,默认返回年
     * @return
     */
    public static long getTimeDiff(LocalDate startTime,LocalDate endTime,String type){
        if (type.equals("days")){
            return startTime.until(endTime, ChronoUnit.DAYS);
        }
        if (type.equals("months")){
            return startTime.until(endTime,ChronoUnit.MONTHS);
        }
        return startTime.until(endTime,ChronoUnit.YEARS);
    }




}