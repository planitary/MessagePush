package com.planitary.message.push.dao;

import lombok.Data;

@Data
public class Anniversary {
    /**
     * 接收方出生日期
     */
    private String receiverBirth;

    /**
     * 发送方出生日期
     */
    private String senderBirth;

    /**
     * 结婚日期
     */
    private String marryDate;

    /**
     * 相爱日期
     */
    private String loveDate;
}
