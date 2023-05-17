package com.planitary.message.push.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.planitary.message.push.config.BaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
@Slf4j
public class InfoHandler {

    @Resource
    private BaseConfig baseConfig;

    public String getMessageInfo(String key){
        if (key == null){
            log.error("参数为空");
            return null;
        }
        RestTemplate restTemplate = new RestTemplate();
        String returnInfo = null;
        try {
            String url = baseConfig.getTxUrl() + "key=" + baseConfig.getTxKey();
            String TxRes = restTemplate.getForObject(url, String.class);
            JSONObject TxJson = JSON.parseObject(TxRes);
            if (TxJson != null) {
                System.out.println(TxJson);
                if (TxJson.get("code").equals(200)) {
                    log.info("{}接口调用成功",url);
                    log.info("接口返回:{}",TxJson);
                    JSONObject data = TxJson.getJSONObject("result");
                    returnInfo = data.getString("content");
                } else {
                    returnInfo = (String) TxJson.get("msg");
                    log.debug("{}接口调用失败,原因:{}",url,returnInfo);
                    return null;
                }
            }
            return returnInfo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnInfo;
    }
}
