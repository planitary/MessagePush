package com.planitary.message.push.Utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
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
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * date转LocalDateTime
     */
    private static LocalDateTime dateConvert2LocalDateTime(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 获取simpleDateFormat的实体
     */

    private static SimpleDateFormat getSimpleDateFormat(){
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
     * @param key           标记位，计算相差天数或小时数,默认返回毫秒
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

    /**
     * 计算从某一时刻开始到现在恋爱了多少天
     * @param loveDate          当前时间
     * @return
     */
    public static long loveDayTillNow(String loveDate){
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat();
        Date loveStartDate;
        LocalDateTime loveStartDateTime = null;
        try {
            loveStartDate = simpleDateFormat.parse(loveDate);
            loveStartDateTime = dateConvert2LocalDateTime(loveStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getTimeDiff(loveStartDateTime,LocalDateTime.now(),"days");
    }

    /**
     * 计算当前日期距离生日还有多少天
     * @param birthDay      生日
     * @return
     */
    public static long currentDayToBirth(String birthDay){
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat();
        Date birthDate;
        LocalDateTime birthDateTime = null;
        try {
            birthDate = simpleDateFormat.parse(birthDay);
            birthDateTime = dateConvert2LocalDateTime(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getTimeDiff(birthDateTime,LocalDateTime.now(),"days");
    }
}