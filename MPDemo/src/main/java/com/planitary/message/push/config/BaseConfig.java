package com.planitary.message.push.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class BaseConfig {
    @Value("${message.push.YyKey}")
    private String YyKey;
    @Value("${message.push.weatherApi}")
    private String weatherApi;

    @Value("${message.push.realTimeApi}")
    private String realTimeWeatherApi;

    @Bean
    public String getYyKey(){
        return YyKey;
    }
    @Bean
    public String getWeatherApi(){
        return weatherApi;
    }

    @Bean
    public String getRealTimeWeatherApi(){
        return realTimeWeatherApi;
    }
}
