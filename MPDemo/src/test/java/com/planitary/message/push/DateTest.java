package com.planitary.message.push;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.planitary.message.push.Utils.DateUtils;
import com.planitary.message.push.config.BaseConfig;
import com.planitary.message.push.dao.Weather;
import com.planitary.message.push.handler.DateHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用RunWith的原因在于DateHandler是springboot项目前部启动后才能初始化的类，我们在单元测试时就注入可能会导致空指针
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DateTest {

    @Resource
    private DateHandler dateHandler;

    @Resource
    private BaseConfig baseConfig;

    @Value("${message.push.loveDay}")
    private String loveDay;

    @Value("${message.push.birthDay}")
    private String birthday;
    @Test
    public void BirthTest(){
        long currentDayToBirth = dateHandler.currentDayToBirth(birthday);
        System.out.println(currentDayToBirth);
    }

    @Test
    public void loveDateTest(){
        String loveDate = loveDay;
        long loveDay = dateHandler.loveDayTillNow(loveDate);
        System.out.println(loveDay);
    }

    @Test
    public void weatherTest(){
        RestTemplate restTemplate = new RestTemplate();
        // get请求,分别对应请求地址，请求参数，响应结果
        String url = baseConfig.getWeatherApi() + "city=" + "112.10.222.164" + "&key=" + baseConfig.getYyKey();
        System.out.println(url);
        String res = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSON.parseObject(res);
//        System.out.println(jsonObject.get("msg"));
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
        System.out.println(weather);
    }
}
