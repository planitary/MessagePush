package com.planitary.message.push.Utils;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import org.springframework.stereotype.Component;

@Component
public class WxPushTemplate {

    // 微信已经限制了推送消息的颜色格式，但是推送方法WxMpTemplateData依然支持，为了方便构造数据，这里做常量填充
    private static final String COLOR_FILL = "00BFFF";
    public WxMpTemplateData createWxTemplateData(String name,String value){
        return new WxMpTemplateData(name,value,COLOR_FILL);
    }
}
