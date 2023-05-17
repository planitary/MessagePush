package com.planitary.message.push.dao;

import lombok.Data;

import javax.sql.rowset.spi.SyncResolver;

@Data
public class Weather {

    /**
     * 最高温最低温
     */
    private String highTemperature;

    private String lowTemperature;

    /**
     * 天气情况
     */
    private String weather_;

    /**
     * 日期
     */
    private String date;

    /**
     * 报告文本
     */
    private String weatherText;

    /**
     * 当前城市
     */
    private String city;

    /**
     * 附加信息
     */
    private String extra;
}
