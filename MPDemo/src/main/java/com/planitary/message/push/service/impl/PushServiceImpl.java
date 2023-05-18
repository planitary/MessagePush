package com.planitary.message.push.service.impl;

import com.planitary.message.push.Utils.IpUtils;
import com.planitary.message.push.Utils.WxPushTemplate;
import com.planitary.message.push.config.BaseConfig;
import com.planitary.message.push.dao.BaseInfo;
import com.planitary.message.push.dao.Weather;
import com.planitary.message.push.handler.DateHandler;
import com.planitary.message.push.handler.InfoHandler;
import com.planitary.message.push.handler.WeatherHandler;
import com.planitary.message.push.service.PushService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
public class PushServiceImpl implements PushService {
    @Resource
    private BaseConfig baseConfig;

    @Resource
    private DateHandler dateHandler;

    @Resource
    private WeatherHandler weatherHandler;

    @Resource
    private InfoHandler infoHandler;

    @Resource
    private WxPushTemplate wxPushTemplate;

    @Override
    public String push() throws IOException {
        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setReceiverName(baseConfig.getUserId());
        baseInfo.setTemplateId(baseConfig.getTemplateId());
        // 微信配置
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(baseConfig.getAppId());
        wxMpInMemoryConfigStorage.setSecret(baseConfig.getSecretKey());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage);

        // 消息推送
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(baseInfo.getReceiverName())
                .templateId(baseInfo.getTemplateId())
                .build();
        // 配置消息
        long loveDays = dateHandler.loveDayTillNow(baseConfig.getLoveDay());
        long birthDays = dateHandler.currentDayToBirth(baseConfig.getBirthDay());
        String info = infoHandler.getMessageInfo(baseConfig.getTxKey());
        log.info("{},{},{}",loveDays,birthDays,info);

        Weather weather = weatherHandler.getWeatherText(baseConfig.getCityCode());
        if (weather == null){
            log.error("获取天气信息失败");
            throw new RuntimeException("获取天气信息失败");
        }
        else {
            log.info("{}",weather);
            templateMessage.addData(wxPushTemplate.createWxTemplateData("today",weather.getDate()));
            templateMessage.addData(wxPushTemplate.createWxTemplateData("city",weather.getCity()));
            templateMessage.addData(wxPushTemplate.createWxTemplateData("tq",weather.getWeather_()));
            templateMessage.addData(wxPushTemplate.createWxTemplateData("currentTemp",weather.getCurrentTemperature()));
            templateMessage.addData(wxPushTemplate.createWxTemplateData("highTemp",weather.getHighTemperature()));
            templateMessage.addData(wxPushTemplate.createWxTemplateData("lowTemp",weather.getLowTemperature()));
            templateMessage.addData(wxPushTemplate.createWxTemplateData("extra",weather.getExtra()));
        }
        templateMessage.addData(wxPushTemplate.createWxTemplateData("loveDays",loveDays + ""));
        templateMessage.addData(wxPushTemplate.createWxTemplateData("birthDays",birthDays + ""));
        String memo = "开启新的一天啦~";
        // LocalDate计算差值的起点是左端点的下一个值,相当于多算了一天
        if ((loveDays - 1) % 365 == 0){
            memo = "今天是我们恋爱" + (loveDays / 365) + "周年纪念日啦!";
        }
        if (birthDays == 0){
            memo = "今天是你的生日耶，祝我的宝宝生日快乐啊~~";
        }
        log.info("info:{}",memo);
        templateMessage.addData(wxPushTemplate.createWxTemplateData("memo", memo));
        templateMessage.addData(wxPushTemplate.createWxTemplateData("info",info));
        log.info("模板信息:{}",templateMessage.toJson());
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("推送失败:{}",e.getMessage());
            return "推送失败";
        }

        return "推送成功!";
    }
}
