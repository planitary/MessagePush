package com.planitary.message.push.handler;

import com.planitary.message.push.Utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class DateHandler {
    /**
     * 计算当前日期距离生日还有多少天
     * @param birthDay      生日
     * @return
     */
    public long currentDayToBirth(String birthDay){
        SimpleDateFormat simpleDateFormat = DateUtils.getSimpleDateFormat();
        Date birthDate;
        LocalDate birthDateTime = null;
        LocalDate currentYearBirthday = null;
        try {
            // 拿到日期的月份，进行比对，如果生日已过，就计算明年的，生日未过，就计算今年的
            birthDate = simpleDateFormat.parse(birthDay);
            birthDateTime = DateUtils.dateConvert2LocalDate(birthDate);
            // 将生日的年份填充为今年
            currentYearBirthday = birthDateTime.withYear(LocalDate.now().getYear());

            // 如果当前日期已经过了今年的生日，就计算明年的生日日期
            if (LocalDate.now().isAfter(currentYearBirthday)){
                currentYearBirthday = currentYearBirthday.plusYears(1);
            }
            if (LocalDate.now().equals(currentYearBirthday)){
                return 0L;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return DateUtils.getTimeDiff(LocalDate.now(),currentYearBirthday,"days");
    }

    /**
     * 计算从某一时刻开始到现在恋爱了多少天
     * @param loveDate          当前时间
     * @return
     */
    public long loveDayTillNow(String loveDate){
        SimpleDateFormat simpleDateFormat = DateUtils.getSimpleDateFormat();
        Date loveStartDate;
        LocalDate loveStartDateTime = null;
        try {
            loveStartDate = simpleDateFormat.parse(loveDate);
            loveStartDateTime = DateUtils.dateConvert2LocalDate(loveStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return DateUtils.getTimeDiff(loveStartDateTime,LocalDate.now(),"days");
    }
}
