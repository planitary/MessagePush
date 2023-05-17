package com.planitary.message.push.dao;

import lombok.Data;

import javax.sql.rowset.spi.SyncResolver;

@Data
/**
 * 天气实体类
 */
public class Weather {

    /**
     * 最高温最低温
     */
    private String highTemperature;

    private String lowTemperature;

    /**
     * 当前天气
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

}
