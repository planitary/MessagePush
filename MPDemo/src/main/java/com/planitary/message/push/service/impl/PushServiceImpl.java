package com.planitary.message.push.service.impl;

import com.planitary.message.push.Utils.IpUtils;
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

    @Autowired
    private WeatherHandler weatherHandler;

    @Autowired
    private InfoHandler infoHandler;

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

        Weather weather = weatherHandler.getWeatherText(IpUtils.getCurrentIPAddress());
        if (weather == null){
            log.error("获取天气信息失败");
            throw new RuntimeException("获取天气信息失败");
        }
        else {
            templateMessage.addData(new WxMpTemplateData("weatherInfo",weather.getWeatherText(),"#00BFFF"));
        }
        templateMessage.addData(new WxMpTemplateData("date",weather.getDate(),"#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("loveDays",loveDays + "","#FF1493"));
        templateMessage.addData(new WxMpTemplateData("birthDays",birthDays + "","#FF1493"));
        String extra = "老婆早上好啊，开启新的一天啦~";
        if (loveDays % 365 == 0){
            extra = "\n今天是" + (loveDays / 365) + "周年纪念日啦!";
        }
        templateMessage.addData(new WxMpTemplateData("remark", extra ,"#FF1493"));
        templateMessage.addData(new WxMpTemplateData("info",info,"#FF69B4"));
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
