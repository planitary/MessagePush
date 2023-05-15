package com.planitary.message.push;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MessagePushApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessagePushApplication.class,args);
        log.info("启动成功");
    }
}
