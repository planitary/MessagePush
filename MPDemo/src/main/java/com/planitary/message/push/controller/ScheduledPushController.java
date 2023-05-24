package com.planitary.message.push.controller;

import com.planitary.message.push.service.PushService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
@Slf4j
public class ScheduledPushController {

    @Autowired
    private PushService pushService;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void Push() throws IOException {
        Random random = new Random();
        int prefix = random.nextInt(99);
        String pushId = String.valueOf(prefix + System.currentTimeMillis());
        log.info("开始推送,推送id:{}",pushId);
        pushService.push();
    }
}
