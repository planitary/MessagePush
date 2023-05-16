package com.planitary.message.push;

import com.planitary.message.push.Utils.DateUtils;
import com.planitary.message.push.config.BaseConfig;
import com.planitary.message.push.handler.DateHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

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

    @Test
    public void BirthTest(){
        String birthday = "1996-01-11";
        long currentDayToBirth = dateHandler.currentDayToBirth(birthday);
        System.out.println(currentDayToBirth);
    }

    @Test
    public void loveDateTest(){
        String loveDate = "2018-03-02";
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
        System.out.println(baseConfig.getYyKey());
        System.out.println(res);
    }
}
