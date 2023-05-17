package com.planitary.message.push.controller;

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
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
public class PushController {
    @Autowired
    PushService pushService;
    @RequestMapping("manualPush")
    public String manualPush() throws IOException {
        return pushService.push();
    }
}
