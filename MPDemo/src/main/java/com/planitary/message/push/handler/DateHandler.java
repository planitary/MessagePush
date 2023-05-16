package com.planitary.message.push.handler;

import com.planitary.message.push.Utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        LocalDateTime birthDateTime = null;
        try {
            // 拿到日期的月份，进行比对，如果生日已过，就计算明年的，生日未过，就计算今年的
            birthDate = simpleDateFormat.parse(birthDay);
            birthDateTime = DateUtils.dateConvert2LocalDateTime(birthDate);

            int birthMonth = birthDateTime.getMonth().getValue();
            int currentMonth = LocalDateTime.now().getMonth().getValue();
            int currentYear = LocalDateTime.now().getYear();

            if (currentMonth < birthMonth){
                // 用当前的时间年份填充生日年份，计算相差值
                LocalDateTime birthOfThisYear = birthDateTime.withYear(currentYear);
                return DateUtils.getTimeDiff(LocalDateTime.now(),birthOfThisYear,"days");
            }
            else {
                // 用明年的时间填充生日年份，计算差值
                LocalDateTime birthOfNextYear = birthDateTime.withYear(currentYear + 1);
                return DateUtils.getTimeDiff(LocalDateTime.now(),birthOfNextYear,"days");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * 计算从某一时刻开始到现在恋爱了多少天
     * @param loveDate          当前时间
     * @return
     */
    public long loveDayTillNow(String loveDate){
        SimpleDateFormat simpleDateFormat = DateUtils.getSimpleDateFormat();
        Date loveStartDate;
        LocalDateTime loveStartDateTime = null;
        try {
            loveStartDate = simpleDateFormat.parse(loveDate);
            loveStartDateTime = DateUtils.dateConvert2LocalDateTime(loveStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return DateUtils.getTimeDiff(loveStartDateTime,LocalDateTime.now(),"days");
    }
}
