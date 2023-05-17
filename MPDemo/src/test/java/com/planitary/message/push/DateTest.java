package com.planitary.message.push;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.planitary.message.push.Utils.DateUtils;
import com.planitary.message.push.Utils.IpUtils;
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
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    @Value("${message.push.common.loveDay}")
    private String loveDay;

    @Value("${message.push.common.birthDay}")
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
        String url = baseConfig.getWeatherFor7DayApi() + "city=" + "112.10.222.164" + "&key=" + baseConfig.getYyKey();
        String realTimeWeatherApi = baseConfig.getRealTimeWeatherApi() +"city=" + "112.10.222.164" + "&key=" + baseConfig.getYyKey();
        System.out.println(url);
        String res = restTemplate.getForObject(url, String.class);
        String realTimeRes = restTemplate.getForObject(realTimeWeatherApi,String.class);
        JSONObject jsonObject = JSON.parseObject(res);
        JSONObject realTimeJson = JSON.parseObject(realTimeRes);

//        System.out.println(jsonObject.get("msg"));
        Weather weather = new Weather();
        if (jsonObject != null && realTimeJson != null){
            if (jsonObject.get("msg").equals("Sucess") && realTimeJson.get("msg").equals("Sucess")) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONObject realTimeData = realTimeJson.getJSONObject("data");
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
                +"度,最高温度:" + weather.getHighTemperature() +
                "度,最低温度:" + weather.getLowTemperature();
        if (weather.getExtra() != null){
            weather.setWeatherText(text + "度," + weather.getExtra());
        }else {
            weather.setWeatherText(text + "度。");
        }
        System.out.println(weather.getWeatherText());
    }

    @Test
    public void TxTest(){
        RestTemplate restTemplate = new RestTemplate();
        String url = baseConfig.getTxUrl() + "key=" + baseConfig.getTxKey();
        String TxRes = restTemplate.getForObject(url, String.class);
        JSONObject TxJson = JSON.parseObject(TxRes);
        if (TxJson != null){
            System.out.println(TxJson);
            if (TxJson.get("code").equals(200)){
                JSONObject data = TxJson.getJSONObject("result");
                String info = data.getString("content");
                System.out.println(info);
            }
            else {
                String errorMsg = (String) TxJson.get("msg");
                System.out.println(errorMsg);
            }
        }
    }

    @Test
    public void test() throws IOException {
        String currentIPAddress = IpUtils.getCurrentIPAddress();
        System.out.println(currentIPAddress);
    }
}
