package com.planitary.message.push.handler;

import com.planitary.message.push.config.BaseConfig;
import com.planitary.message.push.dao.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

public class WeatherHandler {

    @Resource
    private BaseConfig baseConfig;

    public Weather getWeatherByIp(String ip){
        RestTemplate restTemplate = new RestTemplate();
        // get请求,分别对应请求地址，请求参数，响应结果
        restTemplate.getForObject("city=" + ip + "&key=" + baseConfig.getYyKey()
                // get请求,分别对应请求地址，请求参数，响应结果
                ,String.class);
        return null;
    }
}
