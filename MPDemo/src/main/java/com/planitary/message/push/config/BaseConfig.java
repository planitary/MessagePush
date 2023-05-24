package com.planitary.message.push.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaseConfig {
    @Value("${message.push.Yy.YyKey}")
    private String YyKey;

    @Value("${message.push.Yy.weatherFor7DayApi}")
    private String weatherFor7DayApi;

    @Value("${message.push.Yy.realTimeApi}")
    private String realTimeWeatherApi;

    @Value("${message.push.Yy.cityCode}")
    private String cityCode;

    @Value("${message.push.Tx.TxKey}")
    private String TxKey;

    @Value("${message.push.Tx.TxUrl}")
    private String TxUrl;

    @Value("${message.push.Wx.appId}")
    private String appId;

    @Value("${message.push.Wx.secretKey}")
    private String secretKey;

    @Value("${message.push.common.loveDay}")
    private String loveDay;

    @Value("${message.push.common.birthDay}")
    private String birthDay;

    @Value("${message.push.Wx.userId}")
    private List<String> UserId;

    @Value("${message.push.Wx.templateId}")
    private String templateId;

    @Bean
    public String getYyKey(){
        return YyKey;
    }
    @Bean
    public String getWeatherFor7DayApi(){
        return weatherFor7DayApi;
    }

    @Bean
    public String getRealTimeWeatherApi(){
        return realTimeWeatherApi;
    }

    @Bean
    public String getCityCode(){
        return cityCode;
    }

    @Bean
    public String getTxKey(){
        return TxKey;
    }

    @Bean
    public String getTxUrl(){
        return TxUrl;
    }

    @Bean
    public String getAppId(){
        return appId;
    }

    @Bean
    public String getSecretKey(){
        return secretKey;
    }

    @Bean
    public String getLoveDay(){
        return loveDay;
    }

    @Bean
    public String getBirthDay(){
        return birthDay;
    }

    @Bean
    public List<String> getUserId(){
        return UserId;
    }

    @Bean
    public String getTemplateId(){
        return templateId;
    }
}
