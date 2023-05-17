package com.planitary.message.push.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.planitary.message.push.config.BaseConfig;
import com.planitary.message.push.dao.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
public class WeatherHandler {

    @Resource
    private BaseConfig baseConfig;

    public Weather getWeather(String param){
        if (param == null){
            log.error("参数为空");
            return null;
        }
        RestTemplate restTemplate = new RestTemplate();
        try {

            String url = baseConfig.getWeatherApi() + "city=" + param + "&key=" + baseConfig.getYyKey();
            log.info("url:{}", url);
            // get请求,分别对应请求地址，请求参数，响应结果
            String res = restTemplate.getForObject(url, String.class);
            JSONObject resJSON = JSON.parseObject(res);
            return null;
        }catch (Exception e){
            return null;
        }
    }
}
