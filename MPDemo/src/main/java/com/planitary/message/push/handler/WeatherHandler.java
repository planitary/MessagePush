package com.planitary.message.push.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.planitary.message.push.Utils.DateUtils;
import com.planitary.message.push.config.BaseConfig;
import com.planitary.message.push.dao.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Component
public class WeatherHandler {

    @Resource
    private BaseConfig baseConfig;

    public Weather getWeatherText(String param){
        if (param == null){
            log.error("参数为空");
            return null;
        }
        RestTemplate restTemplate = new RestTemplate();
        try {
            // get请求,分别对应请求地址，请求参数，响应结果
            String url = baseConfig.getWeatherFor7DayApi() + "city=" + param + "&key=" + baseConfig.getYyKey();
            String realTimeWeatherApi = baseConfig.getRealTimeWeatherApi() +"city=" + param + "&key=" + baseConfig.getYyKey();
            System.out.println(url);
            String res = restTemplate.getForObject(url, String.class);
            String realTimeRes = restTemplate.getForObject(realTimeWeatherApi,String.class);
            JSONObject jsonObject = JSON.parseObject(res);
            JSONObject realTimeJson = JSON.parseObject(realTimeRes);

            Weather weather = new Weather();
            if (jsonObject != null && realTimeJson != null){
                if (jsonObject.get("msg").equals("Sucess") && realTimeJson.get("msg").equals("Sucess")) {
                    log.info("{}调用成功",url);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject realTimeData = realTimeJson.getJSONObject("data");
                    System.out.println(data);
                    JSONArray list = data.getJSONArray("list");
                    JSONObject todayTemp = (JSONObject) list.get(0);
                    log.info("Json数组:{}",todayTemp);
                    weather.setCity((String) data.get("cityName"));
                    String date = LocalDateTime.now().format(DateUtils.DATE_TIME_FORMATTER);
                    weather.setDate(date);
                    weather.setHighTemperature(todayTemp.getString("qw1"));
                    weather.setLowTemperature(todayTemp.getString("qw2"));
                    if (realTimeData.getString("tq").contains("雨")){
                        weather.setExtra("记得出门带好雨伞哦~");
                    }
                    if (realTimeData.getString("tq").equals("晴")){
                        weather.setExtra("出门记得做好防晒喔~");
                    }
                    if (realTimeData.getString("tq").contains("雾") || todayTemp.getString("tq1").contains("霾")){
                        weather.setExtra("出门注意安全哦~");
                    }
                    weather.setWeather_(realTimeData.getString("tq"));
                    weather.setCurrentTemperature(realTimeData.getString("qw"));
                }
            }
            // 封装报告文本
            String text = "今天是:" + weather.getDate() + "\n" +
                    weather.getCity() + "区当前天气" + weather.getWeather_() + "，当前温度:" + weather.getCurrentTemperature()
                    +"度,今日最高温度:" + weather.getHighTemperature() +
                    "度,最低温度:" + weather.getLowTemperature();
            if (weather.getExtra() != null){
                weather.setWeatherText(text + "度," + weather.getExtra());
            }else {
                weather.setWeatherText(text + "度。");
            }
            return weather;
        }catch (Exception e){
            return null;
        }
    }
}
