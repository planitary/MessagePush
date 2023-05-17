package com.planitary.message.push.service;

import java.io.IOException;

/**
 * 推送业务层接口
 */
public interface PushService {
    /**
     * 业务层主函数
     *
     */
    String push() throws IOException;
}
