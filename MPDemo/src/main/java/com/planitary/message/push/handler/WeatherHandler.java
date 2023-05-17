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
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
public class WeatherHandler {

    private  static final String PRERIX_URL = "city=" + "112.10.222.164" + "&key=";

    @Resource
    private BaseConfig baseConfig;

    public Weather getWeather(String param){
        if (param == null){
            log.error("参数为空");
            return null;
        }
        RestTemplate restTemplate = new RestTemplate();
        try {
            String url = baseConfig.getWeatherApi() + PRERIX_URL +  baseConfig.getYyKey();
            log.info("url:{}", url);
            // get请求,分别对应请求地址，请求参数，响应结果
            String res = restTemplate.getForObject(url, String.class);
            JSONObject jsonObject = JSON.parseObject(res);
            Weather weather = new Weather();
            if (jsonObject != null){
                if (jsonObject.get("msg").equals("Sucess")) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    System.out.println(data);
                    JSONArray list = data.getJSONArray("list");
                    JSONObject todayTemp = (JSONObject) list.get(0);
//                System.out.println(todayTemp);
                    weather.setCity((String) data.get("cityName"));
//                weather.setCurrentTemp((String) jsonObject.get("data."));
                    String date = LocalDateTime.now().format(DateUtils.DATE_TIME_FORMATTER);
                    weather.setDate(date);
                    weather.setHighTemperature(todayTemp.getString("qw1"));
                    weather.setLowTemperature(todayTemp.getString("qw2"));
                    weather.setWeather_(todayTemp.getString("tq1"));
                }
            }
            // 封装报告文本
            String stringBuilder = "今天是:" + weather.getDate() + "\n" +
                    weather.getCity() + "区当前天气" + weather.getWeather_() + ",最高温度:" + weather.getHighTemperature() +
                    "度,最低温度:" + weather.getLowTemperature() + "度。";
            weather.setWeatherText(stringBuilder);
            return weather;
        }catch (Exception e){
            return null;
        }
    }
}
