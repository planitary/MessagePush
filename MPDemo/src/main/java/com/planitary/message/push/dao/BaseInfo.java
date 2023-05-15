package com.planitary.message.push.dao;

import lombok.Data;

@Data
public class BaseInfo {

    /**
     * 接收方名字
     */
    private String receiverName;

    /**
     * 接收方昵称
     */
    private String receiverNickName;

    /**
     * 微信授权信息
     */
    private String appId;
    private String secretKey;

    /**
     * 天行数据
     */
    private String TxKey;

    private String TxSecret;

    /**
     * 模板id
     */
    private String templateId;

}

